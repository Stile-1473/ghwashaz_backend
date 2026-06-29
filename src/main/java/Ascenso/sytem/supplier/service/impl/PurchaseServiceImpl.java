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
import Ascenso.sytem.supplier.dto.CreatePurchaseRequestDto;
import Ascenso.sytem.supplier.dto.PurchaseItemRequestDto;
import Ascenso.sytem.supplier.dto.PurchaseResponseDto;
import Ascenso.sytem.supplier.dto.PurchaseWithItemsResponseDto;
import Ascenso.sytem.supplier.dto.ReceivePurchaseRequestDto;
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
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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
        if (!supplier.getActive()) {
            throw new BadRequestException("Supplier is inactive");
        }

        Purchase purchase = purchaseMapper.toEntity(dto);
        purchase.setSupplier(supplier);
        purchase.setPurchaseNumber(purchaseNumberGenerator.generate());
        purchase.setStatus(PurchaseStatus.DRAFT);

        BigDecimal subtotal = BigDecimal.ZERO;
        List<PurchaseItem> purchaseItems = new ArrayList<>();

        for (PurchaseItemRequestDto itemRequestDto : dto.getItems()) {
            Product product = productValidator.getValidatedProduct(itemRequestDto.getProductId());
            if (!product.getActive()) {
                throw new BadRequestException("Inactive product: " + product.getName());
            }

            BigDecimal lineTotal = itemRequestDto.getUnitCost().multiply(
                    BigDecimal.valueOf(itemRequestDto.getQuantity()));
            subtotal = subtotal.add(lineTotal);

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
        purchase.setTotal(subtotal.subtract(dto.getDiscount()).add(dto.getTax()));

        Purchase saved = purchaseRepository.save(purchase);
        try {
            auditServiceContract.log(AuditModule.PURCHASE, AuditActionType.CREATE, saved.getId(),
                    "Created purchase " + saved.getPurchaseNumber());
        } catch (Exception e) {
            log.warn("Audit log failed for create purchase {}", saved.getPurchaseNumber());
        }
        return purchaseMapper.toResponse(saved);
    }

    @Override
    public PurchaseResponseDto getPurchase(UUID id) {
        return purchaseMapper.toResponse(purchaseValidator.getValidatedPurchase(id));
    }

    @Override
    public PurchaseWithItemsResponseDto getPurchaseWithItems(UUID id) {
        Purchase purchase = purchaseValidator.getValidatedPurchase(id);
        
        return PurchaseWithItemsResponseDto.builder()
                .id(purchase.getId())
                .purchaseNumber(purchase.getPurchaseNumber())
                .status(purchase.getStatus().name())
                .subtotal(purchase.getSubtotal())
                .discount(purchase.getDiscount())
                .tax(purchase.getTax())
                .total(purchase.getTotal())
                .notes(purchase.getNotes())
                .createdAt(purchase.getCreatedAt())
                .supplier(PurchaseWithItemsResponseDto.SupplierDto.builder()
                        .id(purchase.getSupplier().getId())
                        .name(purchase.getSupplier().getCompanyName())
                        .build())
                .items(purchase.getItems().stream()
                        .map(item -> PurchaseWithItemsResponseDto.PurchaseItemWithIdDto.builder()
                                .itemId(item.getId())
                                .productName(item.getProduct().getName())
                                .productSku(item.getProduct().getSku())
                                .orderedQuantity(item.getQuantity())
                                .unitCost(item.getUnitCost())
                                .lineTotal(item.getLineTotal())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public PageResponse<PurchaseResponseDto> getPurchases(String search, UUID supplierId, String status, Pageable pageable) {
        Page<Purchase> page = purchaseRepository.findAll(
                PurchaseSpecification.search(search, supplierId, status), pageable);
        return PageMapper.from(page.map(purchaseMapper::toResponse));
    }

    @Override
    public PurchaseResponseDto receivePurchase(UUID id, ReceivePurchaseRequestDto dto) {
        Purchase purchase = purchaseValidator.getValidatedPurchase(id);
        purchaseValidator.validateDraft(purchase);

        Map<UUID, Integer> receivedMap = dto.getItems().stream()
                .collect(Collectors.toMap(item -> item.getItemId(), item -> item.getReceivedQuantity()));

        boolean hasShortage = false;
        int totalReceived = 0;
        int totalOrdered = 0;

        for (PurchaseItem item : purchase.getItems()) {
            Integer receivedQty = receivedMap.get(item.getId());
            int orderedQty = item.getQuantity();
            totalOrdered += orderedQty;
            
            if (receivedQty == null || receivedQty < 0) {
                throw new BadRequestException(
                        "Invalid received quantity for item: " + item.getProduct().getName());
            }
            
            if (receivedQty > orderedQty) {
                throw new BadRequestException(
                        "Cannot receive more than ordered for: " + item.getProduct().getName());
            }
            
            if (receivedQty < orderedQty) {
                hasShortage = true;
            }
            totalReceived += receivedQty;
        }

        if (hasShortage) {
            purchase.setStatus(PurchaseStatus.PARTIAL);
            if (dto.getNotes() != null && !dto.getNotes().isBlank()) {
                purchase.setNotes(dto.getNotes());
            }
            Purchase saved = purchaseRepository.save(purchase);
            try {
                auditServiceContract.log(AuditModule.PURCHASE, AuditActionType.RECEIVE, saved.getId(),
                        "Partial delivery: " + totalReceived + "/" + totalOrdered);
            } catch (Exception e) {
                log.warn("Audit log failed");
            }
            return purchaseMapper.toResponse(saved);
        } else {
            for (PurchaseItem item : purchase.getItems()) {
                Integer receivedQty = receivedMap.get(item.getId());
                if (receivedQty != null && receivedQty > 0) {
                    inventoryService.receiveStock(item.getProduct(), receivedQty);
                }
            }
            purchase.setStatus(PurchaseStatus.RECEIVED);
            if (dto.getNotes() != null && !dto.getNotes().isBlank()) {
                purchase.setNotes(dto.getNotes());
            }
            Purchase saved = purchaseRepository.save(purchase);
            try {
                auditServiceContract.log(AuditModule.PURCHASE, AuditActionType.RECEIVE, saved.getId(),
                        "Received purchase " + saved.getPurchaseNumber());
            } catch (Exception e) {
                log.warn("Audit log failed");
            }
            return purchaseMapper.toResponse(saved);
        }
    }

    @Override
    public PurchaseResponseDto approveShortDelivery(UUID id) {
        Purchase purchase = purchaseValidator.getValidatedPurchase(id);
        
        if (purchase.getStatus() != PurchaseStatus.PARTIAL) {
            throw new BadRequestException("No partial delivery to approve for this purchase");
        }

        for (PurchaseItem item : purchase.getItems()) {
            int qty = item.getQuantity();
            if (qty > 0) {
                inventoryService.receiveStock(item.getProduct(), qty);
            }
        }

        purchase.setStatus(PurchaseStatus.RECEIVED);
        Purchase saved = purchaseRepository.save(purchase);
        try {
            auditServiceContract.log(AuditModule.PURCHASE, AuditActionType.RECEIVE, saved.getId(),
                    "Short delivery approved for " + saved.getPurchaseNumber());
        } catch (Exception e) {
            log.warn("Audit log failed");
        }
        return purchaseMapper.toResponse(saved);
    }

    @Override
    public void cancelPurchase(UUID id) {
        Purchase purchase = purchaseValidator.getValidatedPurchase(id);
        purchaseValidator.validateDraft(purchase);
        purchase.setStatus(PurchaseStatus.CANCELLED);
        purchaseRepository.save(purchase);
        try {
            auditServiceContract.log(AuditModule.PURCHASE, AuditActionType.DELETE, purchase.getId(),
                    "Cancelled purchase " + purchase.getPurchaseNumber());
        } catch (Exception e) {
            log.warn("Audit log failed for purchase cancel {}", purchase.getPurchaseNumber());
        }
    }
}
