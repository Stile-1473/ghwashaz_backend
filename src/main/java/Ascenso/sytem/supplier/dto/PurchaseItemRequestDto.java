package Ascenso.sytem.supplier.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Getter
@Setter
public class PurchaseItemRequestDto {

    @NotNull(message = "Product is required")
    private UUID productId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be greater than zero")
    private Integer quantity;

    @NotNull(message = "Unit cost is required")
    @DecimalMin(value = "0.00")
    private BigDecimal unitCost;

}