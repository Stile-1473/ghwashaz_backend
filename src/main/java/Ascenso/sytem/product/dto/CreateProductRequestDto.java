package Ascenso.sytem.product.dto;

import Ascenso.sytem.common.enums.Unit;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CreateProductRequestDto {

    //product name must be unique
    //category must exist

    @NotBlank(message = "Product name is required")
    @Size(max = 150)
    private String name;

    @Size(max = 1000)
    private String description;

    @NotNull(message = "Category is required")
    private UUID categoryId;

    @NotNull(message = "Selling price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Selling price must be greater than zero")
    private BigDecimal sellingPrice;

    @NotNull(message = "Cost price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Cost price must be greater than zero")
    private BigDecimal costPrice;


    @NotNull(message = "Unit is required")
    private Unit productUnit;

    private String Barcode;

    @NotNull(message = "Minimum stock level is required")
    @Min(0)
    private Integer minimumStockLevel;
}
