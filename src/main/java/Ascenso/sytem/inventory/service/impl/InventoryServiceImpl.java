package Ascenso.sytem.inventory.service.impl;

import Ascenso.sytem.audit.service.AuditServiceContract;
import Ascenso.sytem.common.enums.AuditActionType;
import Ascenso.sytem.common.enums.AuditModule;
import Ascenso.sytem.common.enums.InventoryMovementType;
import Ascenso.sytem.common.enums.NotificationType;
import Ascenso.sytem.common.response.PageResponse;
import Ascenso.sytem.inventory.dto.InventoryResponseDto;
import Ascenso.sytem.inventory.dto.StockAdjustmentRequestDto;
import Ascenso.sytem.inventory.entity.Inventory;
import Ascenso.sytem.inventory.entity.InventoryMovement;
import Ascenso.sytem.inventory.mapper.InventoryMapper;
import Ascenso.sytem.inventory.repository.InventoryMovementRepository;
import Ascenso.sytem.inventory.repository.InventoryRepository;
import Ascenso.sytem.inventory.service.InventoryServiceContract;
import Ascenso.sytem.inventory.validator.InventoryValidator;
import Ascenso.sytem.product.entity.Product;
import Ascenso.sytem.product.validator.ProductValidator;
import Ascenso.sytem.system.service.NotificationServiceContract;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InventoryServiceImpl implements InventoryServiceContract {

    private final InventoryRepository inventoryRepository;
    private final InventoryMovementRepository movementRepository;
    private final InventoryValidator inventoryValidator;
    private final ProductValidator productValidator;
    private final InventoryMapper inventoryMapper;
    private final AuditServiceContract auditServiceContract;
    private final NotificationServiceContract notificationServiceContract;

    @Override
    public void createInventory(Product product) {
        Inventory inventory = Inventory.builder()
                .product(product)
                .quantityAvailable(0)
                .quantityOnHand(0)
                .quantityReserved(0)
                .build();
        inventoryRepository.save(inventory);
        log.info("Inventory initialized for product {}",product.getName());
        try {
            auditServiceContract.log(
                    AuditModule.INVENTORY,
                    AuditActionType.CREATE,
                    inventory.getId(),
                    "Created inventory for " + product.getName()
            );
        } catch (Exception e){
            log.warn("Audit log failed for inventory create of {}, error={}",product.getName(),e.getMessage());
        }
    }

    @Override
    public void receiveStock(Product product, Integer quantity) {
        Inventory inventory = inventoryValidator.getInventory(product);
        inventory.setQuantityOnHand(inventory.getQuantityOnHand() + quantity);
        inventory.setQuantityAvailable(inventory.getQuantityOnHand() - inventory.getQuantityReserved());
        inventoryRepository.save(inventory);
        saveMovement(product, InventoryMovementType.PURCHASE, quantity, inventory.getQuantityOnHand(), "PURCHASE", null);
        try {
            auditServiceContract.log(AuditModule.INVENTORY, AuditActionType.UPDATE, inventory.getId(),
                    "Received stock for " + product.getName());
        } catch (Exception e) {
            log.warn("Audit log failed for receiving stock of {}, error={}",product.getName(),e.getMessage());
        }
        // Check for low stock after receiving
        checkLowStockAndNotify(inventory);
    }

    @Override
    public void reduceStock(Product product, Integer quantity, String reference, String remarks){
        Inventory inventory = inventoryValidator.getInventory(product);
        inventoryValidator.validateStockAvailable(inventory, quantity);
        inventory.setQuantityOnHand(inventory.getQuantityOnHand() - quantity);
        inventory.setQuantityAvailable(inventory.getQuantityOnHand() - inventory.getQuantityReserved());
        inventoryRepository.save(inventory);
        saveMovement(product, InventoryMovementType.SALE, -quantity, inventory.getQuantityOnHand(), reference, remarks);
        try {
            auditServiceContract.log(AuditModule.INVENTORY, AuditActionType.UPDATE, inventory.getId(),
                    "Reduced stock for " + product.getName());
        } catch (Exception e) {
            log.warn("Audit log failed for reducing stock of {}, error={}",product.getName(),e.getMessage());
        }
        // Check for low stock after reducing
        checkLowStockAndNotify(inventory);
    }

    @Override
    public void adjustStock(UUID productId, StockAdjustmentRequestDto requestDto) {
        Product product = productValidator.getValidatedProduct(productId);
        Inventory inventory = inventoryValidator.getInventory(product);
        int delta = requestDto.getQuantity();
        if(delta < 0){
            int decreaseAmount = Math.abs(delta);
            inventoryValidator.validateStockAvailable(inventory, decreaseAmount);
            inventory.setQuantityOnHand(inventory.getQuantityOnHand() - decreaseAmount);
        }else{
            inventory.setQuantityOnHand(inventory.getQuantityOnHand() + delta);
        }
        inventory.setQuantityAvailable(inventory.getQuantityOnHand() - inventory.getQuantityReserved());
        inventoryRepository.save(inventory);
        saveMovement(product, InventoryMovementType.ADJUSTMENT, delta, inventory.getQuantityOnHand(), "Manual", requestDto.getReason());
        try {
            auditServiceContract.log(AuditModule.INVENTORY, AuditActionType.UPDATE, inventory.getId(),
                    "Stock adjusted for " + product.getName());
        } catch (Exception e) {
            log.warn("Audit log failed for stock adjustment of {}, error={}",product.getName(),e.getMessage());
        }
        // Check for low stock after adjusting
        checkLowStockAndNotify(inventory);
    }

    @Override
    public InventoryResponseDto getInventory(UUID productId) {
        Product product = productValidator.getValidatedProduct(productId);
        return inventoryMapper.toResponse(inventoryValidator.getInventory(product));
    }

    @Override
    public PageResponse<InventoryResponseDto> getInventory(String search, Boolean lowStock, Pageable pageable) {
        return Ascenso.sytem.common.mapper.PageMapper.from(
                inventoryRepository.findAll(
                        Ascenso.sytem.inventory.specification.InventorySpecification.search(search, lowStock),
                        pageable
                ).map(inventoryMapper::toResponse)
        );
    }

    private void saveMovement(Product product, InventoryMovementType movtype, Integer quantity, 
                            Integer balance, String reference, String remarks){
        InventoryMovement movement = InventoryMovement.builder()
                .product(product)
                .type(movtype)
                .quantity(quantity)
                .balanceAfterMovement(balance)
                .reference(reference)
                .remarks(remarks)
                .build();
        movementRepository.save(movement);
    }

    private void checkLowStockAndNotify(Inventory inventory) {
        Product product = inventory.getProduct();
        Integer minLevel = product.getMinimumStockLevel();
        if (minLevel == null || minLevel <= 0) {
            return;
        }
        if (inventory.getQuantityOnHand() <= minLevel) {
            try {
                notificationServiceContract.createNotification(
                        "Low Stock Alert",
                        product.getName() + " is running low. Current stock: " + inventory.getQuantityOnHand() 
                        + ". Minimum level: " + minLevel,
                        NotificationType.LOW_STOCK
                );
                log.info("Low stock notification sent for {}", product.getName());
            } catch (Exception e) {
                log.warn("Failed to send low stock notification for {}: {}", product.getName(), e.getMessage());
            }
        }
    }
}
