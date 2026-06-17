package Ascenso.sytem.report.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class DashboardResponseDto {

    private BigDecimal totalSales;

    private BigDecimal totalProfit;


    private Integer todayTransactions;

    private BigDecimal monthProfit;
    private BigDecimal monthsSales;

    private Integer totalProducts;
    private Integer lowStockProducts;

    private Integer totalCustomers;
    private Integer totalCashiers;


}
