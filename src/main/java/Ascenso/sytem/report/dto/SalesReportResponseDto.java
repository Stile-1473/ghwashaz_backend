package Ascenso.sytem.report.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesReportResponseDto {

    private BigDecimal totalSales;
    private Long transactionCount;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<SaleDto> sales;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaleDto {
        private String saleNumber;
        private BigDecimal totalAmount;
        private String paymentMethod;
        private String cashier;
        private LocalDateTime date;
    }
}
