package Ascenso.sytem.product.dto;

import Ascenso.sytem.common.enums.Unit;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ProductResponseDto {

 private UUID id;
 private String name;
 private String description;
 private String categoryName;
 private BigDecimal sellingPrice;
 private BigDecimal costPrice;
 private Unit unit;
 private String barcode;
 private Integer minimumStockLevel;
 private Boolean lowStock;
 private Boolean active;
 private LocalDateTime createdAt;

}
