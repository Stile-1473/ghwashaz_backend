package Ascenso.sytem.inventory.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class InventoryResponseDto {

    private UUID inventoryId;

    private UUID productId;

    private String sku;

    private String productName;

    private String category;

    private Integer quantityAvailable;

    private Integer quantityOnHand;

    private Integer quantityReserved;

    private Integer minimumStockLevel;

    private Boolean lowStock;

    private LocalDateTime lastUpdated;
}
