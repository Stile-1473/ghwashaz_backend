package Ascenso.sytem.product.dto;

import Ascenso.sytem.common.enums.Unit;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class UpdateProductRequestDto {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private UUID categoryId;

    private String barcode;

    @NotNull
    private BigDecimal sellingPrice;

    @NotNull
    private BigDecimal costPrice;

    @NotNull
    private Unit productUnit;

    @Min(0)
    private Integer minimumStockLevel;


}
