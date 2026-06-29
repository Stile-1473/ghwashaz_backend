package Ascenso.sytem.finance.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashSummaryDto {

    private BigDecimal totalCashIn;
    private BigDecimal totalCashOut;
    private BigDecimal netBalance;
    private Long transactionCount;
}
