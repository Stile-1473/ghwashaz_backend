package Ascenso.sytem.finance.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashBalanceDto {

    private BigDecimal currentBalance;
    private BigDecimal sessionOpeningBalance;
    private BigDecimal sessionCashIn;
    private BigDecimal sessionCashOut;
}
