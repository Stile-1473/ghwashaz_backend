package Ascenso.sytem.inventory.dto;

import Ascenso.sytem.common.enums.InventoryMovementType;
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
public class InventoryMovementResponseDto {

    private UUID id;

    private UUID productId;

    private String productName;

    private InventoryMovementType type;

    private Integer quantity;


    private Integer balanceAfterMovement;

    private String reference;

    private String remarks;

    private String performedBy;

    private LocalDateTime createdAt;
}
