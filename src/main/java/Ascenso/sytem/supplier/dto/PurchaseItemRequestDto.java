package Ascenso.sytem.supplier.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PurchaseItemRequestDto {

    @NotNull
    private UUID productId;

    @NotNull
    @Min(1)
    private Integer quantityOrdered;
}
