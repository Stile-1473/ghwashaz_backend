package Ascenso.sytem.report.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryReportResponseDto {

    private Long totalProducts;
    private Long totalQuantity;
    private BigDecimal totalValue;
    private BigDecimal totalCost;
    private BigDecimal potentialProfit;
    private List<ProductStockDto> lowStock;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductStockDto {
        private String productName;
        private Long quantity;
        private Long reorderLevel;
    }
}
