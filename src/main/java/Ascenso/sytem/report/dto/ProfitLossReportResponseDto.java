package Ascenso.sytem.report.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfitLossReportResponseDto {

    private BigDecimal totalRevenue;
    private BigDecimal totalCost;
    private BigDecimal grossProfit;
    private BigDecimal totalExpenses;
    private BigDecimal netProfit;
    private Integer transactionCount;
}
