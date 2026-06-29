package Ascenso.sytem.report.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseReportResponseDto {

    private BigDecimal totalExpenses;
    private Long transactionCount;
    private List<CategoryExpenseDto> byCategory;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryExpenseDto {
        private String category;
        private BigDecimal total;
        private Long count;
    }
}
