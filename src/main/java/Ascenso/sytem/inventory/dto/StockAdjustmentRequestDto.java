package Ascenso.sytem.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class StockAdjustmentRequestDto {
    @NotNull
    private UUID productId;

    @NotNull
    private Integer newQuantity;

    @NotBlank
    private String reason;
}
