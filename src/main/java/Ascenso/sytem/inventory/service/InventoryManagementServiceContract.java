package Ascenso.sytem.inventory.service;

import Ascenso.sytem.common.enums.StockMovementType;

import java.util.UUID;

public interface InventoryManagementServiceContract {
    void reduceStock(
            UUID productId,
            Integer quantity,
            StockMovementType stockMovementType,
            UUID referenceId

    );

    void increaseStock(
            UUID productId,
            Integer quantity,
            StockMovementType type,
            UUID referenceId
    );

}
