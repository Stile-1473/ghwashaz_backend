package Ascenso.sytem.sale.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class SaleItemResponseDto {

    private String productName;

    private Integer quantity;

    private BigDecimal priceAtSale;

    private BigDecimal totalPrice;


}
