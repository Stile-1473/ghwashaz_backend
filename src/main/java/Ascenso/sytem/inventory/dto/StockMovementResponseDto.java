package Ascenso.sytem.inventory.dto;

import Ascenso.sytem.common.enums.StockMovementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
