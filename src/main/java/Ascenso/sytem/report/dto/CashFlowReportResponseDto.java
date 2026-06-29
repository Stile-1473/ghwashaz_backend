package Ascenso.sytem.report.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashFlowReportResponseDto {

    private BigDecimal totalCashIn;
    private BigDecimal totalCashOut;
    private BigDecimal netChange;
    private List<TransactionDto> transactions;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransactionDto {
        private String transactionNumber;
        private String type;
        private BigDecimal amount;
        private String reason;
        private String performedBy;
    }
}
