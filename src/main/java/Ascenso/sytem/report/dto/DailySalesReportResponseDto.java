package Ascenso.sytem.report.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Daily Sales Summary Report DTO
 * 
 * Returns:
 * - totalSales
 * - totalExpenses
 * - netCash
 * - topProducts
 * - topCustomers
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailySalesReportResponseDto {
    
    private BigDecimal totalSales;
    private BigDecimal totalExpenses;
    private BigDecimal netCash;
    private Integer totalTransactions;
    private List<TopProductDto> topProducts;
    private List<TopCustomerDto> topCustomers;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopProductDto {
        private String productName;
        private Integer quantitySold;
        private BigDecimal revenue;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopCustomerDto {
        private String customerName;
        private Integer visitCount;
        private BigDecimal totalSpent;
    }
}
