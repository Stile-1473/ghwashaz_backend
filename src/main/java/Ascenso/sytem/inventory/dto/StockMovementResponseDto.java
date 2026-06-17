package Ascenso.sytem.inventory.dto;

import Ascenso.sytem.common.enums.StockMovementType;

import java.time.LocalDateTime;
import java.util.UUID;

public class StockMovementResponseDto {

    private UUID id;

    private String productName;

    private StockMovementType type;

    private Integer quantityChanged;

    private Integer previousQuantity;

    private Integer newQuantity;

    private String reason;

    private String performedBy;

    private LocalDateTime createdAt;
}
