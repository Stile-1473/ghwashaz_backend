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
    private UUID productId;

    private String productName;

    private String categoryName;

    private Integer quantity;

    private Integer minimumStockLevel;

    private Boolean lowStock;

    private LocalDateTime lastUpdated;
}
