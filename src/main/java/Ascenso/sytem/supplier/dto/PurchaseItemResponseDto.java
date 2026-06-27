package Ascenso.sytem.supplier.dto;


import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class PurchaseItemResponseDto {

    private UUID productId;

    private String productName;

    private String sku;

    private Integer quantity;

    private BigDecimal unitCost;

    private BigDecimal lineTotal;

}