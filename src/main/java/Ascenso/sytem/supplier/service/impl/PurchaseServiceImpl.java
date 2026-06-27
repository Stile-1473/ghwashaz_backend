package Ascenso.sytem.supplier.service.impl;

import Ascenso.sytem.audit.service.AuditServiceContract;
import Ascenso.sytem.common.enums.AuditActionType;
import Ascenso.sytem.common.enums.AuditModule;
import Ascenso.sytem.common.enums.PurchaseStatus;
import Ascenso.sytem.common.exception.BadRequestException;
import Ascenso.sytem.common.mapper.PageMapper;
import Ascenso.sytem.common.response.PageResponse;
import Ascenso.sytem.inventory.service.InventoryServiceContract;
import Ascenso.sytem.product.entity.Product;
import Ascenso.sytem.product.validator.ProductValidator;
import Ascenso.sytem.purchase.dto.CreatePurchaseRequestDto;
import Ascenso.sytem.supplier.dto.PurchaseItemRequestDto;
import Ascenso.sytem.supplier.dto.PurchaseResponseDto;
import Ascenso.sytem.supplier.entity.Purchase;
import Ascenso.sytem.supplier.entity.PurchaseItem;
import Ascenso.sytem.supplier.entity.Supplier;
import Ascenso.sytem.supplier.mapper.PurchaseMapper;
import Ascenso.sytem.supplier.repository.PurchaseRepository;
import Ascenso.sytem.supplier.service.PurchaseServiceContract;
import Ascenso.sytem.supplier.specification.PurchaseSpecification;
import Ascenso.sytem.supplier.util.PurchaseNumberGenerator;
import Ascenso.sytem.supplier.validator.PurchaseValidator;
import Ascenso.sytem.supplier.validator.SupplierValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PurchaseServiceImpl implements PurchaseServiceContract {

    private final PurchaseRepository purchaseRepository;

    private final InventoryServiceContract inventoryService;



    private final PurchaseMapper purchaseMapper;

    private final PurchaseValidator purchaseValidator;

    private final PurchaseNumberGenerator purchaseNumberGenerator;

    private final SupplierValidator supplierValidator;

    private final ProductValidator productValidator;

    private final AuditServiceContract auditServiceContract;

    @Override
    public PurchaseResponseDto createPurchase(CreatePurchaseRequestDto dto) {

        Supplier supplier = supplierValidator.getValidatedSupplier(dto.getSupplierId());

        if(!supplier.getActive()){
            throw new BadRequestException(
                    "Supplier is inactive"
            );
        }

        Purchase purchase = purchaseMapper.toEntity(dto);

        purchase.setSupplier(supplier);

        purchase.setPurchaseNumber(purchaseNumberGenerator.generate());

        purchase.setStatus(PurchaseStatus.DRAFT);

        BigDecimal subtotal = BigDecimal.ZERO;

        List<PurchaseItem> purchaseItems = new ArrayList<>();

        for(PurchaseItemRequestDto itemRequestDto : dto.getItems()){

            Product product = productValidator.getValidatedProduct(itemRequestDto.getProductId());

            if(!product.getActive()){

                throw new BadRequestException(
                        "Inactive product" + product.getName()
                );

            }

            BigDecimal lineTotal = itemRequestDto.getUnitCost().multiply(
                    BigDecimal.valueOf(
                            itemRequestDto.getQuantity()
                    )
            );

            subtotal =  subtotal.add(lineTotal);


            PurchaseItem item = PurchaseItem.builder()
                    .purchase(purchase)
                    .product(product)
                    .quantity(itemRequestDto.getQuantity())
                    .unitCost(itemRequestDto.getUnitCost())
                    .lineTotal(lineTotal)
                    .build();

            purchaseItems.add(item);

        }

        purchase.setItems(purchaseItems);

        purchase.setSubtotal(subtotal);

        purchase.setTotal(
              subtotal
                      .subtract(dto.getDiscount())
                      .add(dto.getTax())
        );


        Purchase saved = purchaseRepository.save(purchase);

        try{
            auditServiceContract.log(

                    AuditModule.PURCHASE,

                    AuditActionType.CREATE,

                    saved.getId(),

                    "Created purchase "
                            + saved.getPurchaseNumber()

            );

        }catch (Exception e){

            log.warn("Audit log failed for create purchase for{}", saved.getPurchaseNumber());
        }



    return purchaseMapper.toResponse(saved);


    }

    @Override
    public PurchaseResponseDto getPurchase(UUID id) {

        return purchaseMapper.toResponse(

                purchaseValidator.getValidatedPurchase(id)

        );
    }

    @Override
    public PageResponse<PurchaseResponseDto> getPurchases(String search, UUID supplierId, String status, Pageable pageable) {

        Page<Purchase> page = purchaseRepository.findAll(PurchaseSpecification.search(
                search,
                supplierId,
                status
        ),
                pageable
                );

        return PageMapper.from(
                page.map(
                        purchaseMapper::toResponse
                )
        );

    }

    @Override
    public PurchaseResponseDto receivePurchase(UUID id) {

        Purchase purchase = purchaseValidator.getValidatedPurchase(id);


        purchaseValidator.validateDraft(purchase);

        for(PurchaseItem item :  purchase.getItems()){

            inventoryService.receiveStock(
                    item.getProduct(),
                    item.getQuantity()
            );




        }

        purchase.setStatus(PurchaseStatus.RECEIVED);

        Purchase saved = purchaseRepository.save(purchase);
        try{
            auditServiceContract.log(

                    AuditModule.PURCHASE,

                    AuditActionType.RECEIVE,

                    saved.getId(),

                    "Received purchase "
                            + saved.getPurchaseNumber()

            );

        }catch(Exception e){

            log.warn(
                    "Audit log failed for purchase receive {} , error={}",
                    purchase.getPurchaseNumber(),
                    e.getMessage()
            );

        }

        return  purchaseMapper.toResponse(saved);

    }

    @Override
    public void cancelPurchase(UUID id) {


        Purchase purchase =
                purchaseValidator.getValidatedPurchase(id);

        purchaseValidator.validateDraft(purchase);

        purchase.setStatus(
                PurchaseStatus.CANCELLED
        );

        purchaseRepository.save(purchase);

        try{
            auditServiceContract.log(

                    AuditModule.PURCHASE,

                    AuditActionType.DELETE,

                    purchase.getId(),

                    "Cancelled purchase "
                            + purchase.getPurchaseNumber()

            );
        }catch (Exception e){
             log.warn(
                     "Audit log failed for purchase cancel {}",purchase.getPurchaseNumber()
             );

        }

    }
}
