package Ascenso.sytem.finance.dto;

import Ascenso.sytem.common.enums.CashSource;
import Ascenso.sytem.common.enums.CashTransactionType;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashTransactionQueryDto {

    private CashTransactionType transactionType;
    private Boolean cashIn;
    private CashSource source;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
