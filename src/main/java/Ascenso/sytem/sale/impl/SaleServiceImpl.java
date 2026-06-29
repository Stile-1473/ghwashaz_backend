package Ascenso.sytem.sale.impl;

import Ascenso.sytem.audit.service.AuditServiceContract;
import Ascenso.sytem.cashier.entity.CashierSession;
import Ascenso.sytem.cashier.validator.CashierSessionValidator;
import Ascenso.sytem.common.enums.AuditActionType;
import Ascenso.sytem.common.enums.AuditModule;
import Ascenso.sytem.common.enums.NotificationType;
import Ascenso.sytem.common.enums.PaymentMethod;
import Ascenso.sytem.common.enums.SaleStatus;
import Ascenso.sytem.common.exception.BadRequestException;
import Ascenso.sytem.customer.entity.Customer;
import Ascenso.sytem.customer.validator.CustomerValidator;
import Ascenso.sytem.inventory.entity.Inventory;
import Ascenso.sytem.inventory.service.InventoryServiceContract;
import Ascenso.sytem.inventory.validator.InventoryValidator;
import Ascenso.sytem.product.entity.Product;
import Ascenso.sytem.product.validator.ProductValidator;
import Ascenso.sytem.sale.dto.CreateSaleRequestDto;
import Ascenso.sytem.sale.dto.ReceiptResponseDto;
import Ascenso.sytem.sale.dto.SaleResponseDto;
import Ascenso.sytem.sale.entity.Sale;
import Ascenso.sytem.sale.entity.SaleItem;
import Ascenso.sytem.sale.entity.SalePayment;
import Ascenso.sytem.sale.mapper.ReceiptMapper;
import Ascenso.sytem.sale.mapper.SaleMapper;
import Ascenso.sytem.sale.repository.SaleRepository;
import Ascenso.sytem.sale.service.SaleServiceContract;
import Ascenso.sytem.sale.specification.SaleSpecification;
import Ascenso.sytem.sale.util.SaleNumberGenerator;
import Ascenso.sytem.sale.validator.SaleValidator;
import Ascenso.sytem.security.util.SecurityUtils;
import Ascenso.sytem.system.service.NotificationServiceContract;
import Ascenso.sytem.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SaleServiceImpl implements SaleServiceContract {

    private final SaleRepository saleRepository;
    private final ProductValidator productValidator;
    private final CustomerValidator customerValidator;
    private final CashierSessionValidator cashierSessionValidator;
    private final InventoryServiceContract inventoryService;
    private final InventoryValidator inventoryValidator;
    private final AuditServiceContract auditService;
    private final NotificationServiceContract notificationService;
    private final SaleNumberGenerator saleNumberGenerator;
    private final SaleValidator saleValidator;
    private final SaleMapper saleMapper;
    private final ReceiptMapper receiptMapper;

    @Override
    public SaleResponseDto createSale(CreateSaleRequestDto dto) {

        User cashier = SecurityUtils.getCurrentUser().getUser();


        CashierSession session = cashierSessionValidator.getOpenSession(cashier);
        Customer customer = null;

        if (dto.getCustomerId() != null) {
            customer = customerValidator.getValidatedCustomer(dto.getCustomerId());
        }

        Sale sale = Sale.builder()
                .saleNumber(saleNumberGenerator.generate())
                .cashier(cashier).cashierSession(session)
                .customer(customer).status(SaleStatus.COMPLETED)
                .notes(dto.getNotes())
                .build();

        BigDecimal subtotal = BigDecimal.ZERO;

        for (var itemDto : dto.getItems()) {

            Product product = productValidator.getValidatedProduct(itemDto.getProductId());

            Inventory inv = inventoryValidator.getInventory(product);

            if (inv.getQuantityAvailable() < itemDto.getQuantity()) {
                throw new BadRequestException("Insufficient stock for product: " + product.getName());
            }

            BigDecimal lineTotal = product.getSellingPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));

            SaleItem saleItem = SaleItem.builder().sale(sale).product(product).quantity(itemDto.getQuantity())
                    .unitPrice(product.getSellingPrice())
                    .costPrice(product.getCostPrice() != null ? product.getCostPrice() : BigDecimal.ZERO)
                    .discount(BigDecimal.ZERO).lineTotal(lineTotal)
                    .build();



            sale.getItems().add(saleItem);

            subtotal = subtotal.add(lineTotal);

            inventoryService.reduceStock(product, itemDto.getQuantity(), sale.getSaleNumber(), "Retail Sale");
        }



        sale.setSubtotal(subtotal);

        sale.setDiscount(dto.getDiscount() != null ? dto.getDiscount() : BigDecimal.ZERO);

        sale.setTax(BigDecimal.ZERO);

        sale.setTotal(subtotal.subtract(sale.getDiscount()).add(sale.getTax()));

        PaymentMethod paymentMethod = dto.getPaymentMethod() != null ? dto.getPaymentMethod() : PaymentMethod.CASH;

        SalePayment payment = SalePayment.builder()
                .sale(sale)
                .paymentMethod(paymentMethod)
                .amount(sale.getTotal())
                .build();

        sale.getPayments().add(payment);

        Sale saved = saleRepository.save(sale);


        try {
            auditService.log(AuditModule.SALE, AuditActionType.CREATE, saved.getId(), "Created sale " + saved.getSaleNumber());
        } catch (Exception e) {
            log.warn("Audit log failed for sale create: {}", e.getMessage());
        }
        log.info("Sale created: {} with total {}", saved.getSaleNumber(), saved.getTotal());
        return saleMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public SaleResponseDto getSale(UUID id) {

        Sale sale = saleValidator.getValidatedSale(id);

        return saleMapper.toResponse(sale);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SaleResponseDto> getSales(LocalDate startDate, LocalDate endDate, UUID cashierId, UUID customerId,

            PaymentMethod paymentMethod, SaleStatus saleStatus, Pageable pageable) {

        Page<Sale> page = saleRepository.findAll(

                SaleSpecification.search(null, cashierId, customerId, saleStatus, startDate, endDate), pageable);
        return page.map(saleMapper::toResponse);
    }

    @Override
    @Transactional
    public void voidSale(UUID id) {
        // Need transaction for lazy loading

        Sale sale = saleValidator.getValidatedSale(id);

        saleValidator.validateCanVoid(sale);

        
        for (SaleItem item : sale.getItems()) {
            inventoryService.receiveStock(item.getProduct(), item.getQuantity());
        }
        
        sale.setStatus(SaleStatus.CANCELLED);

        saleRepository.save(sale);
        
        try {
            auditService.log(AuditModule.SALE, AuditActionType.UPDATE, sale.getId(), "Voided sale " + sale.getSaleNumber());
            notificationService.createNotification(
                    "Sale Voided",
                    "Sale " + sale.getSaleNumber() + " has been voided. Inventory restored.",
                    NotificationType.SALE_VOIDED
            );
        } catch (Exception e) {
            log.warn("Audit log failed for sale void: {}", e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ReceiptResponseDto getReceipt(UUID id) {

        Sale sale = saleValidator.getValidatedSale(id);

        return receiptMapper.toResponse(sale);
    }

}
