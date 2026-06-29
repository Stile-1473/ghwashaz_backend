package Ascenso.sytem.finance.dto;

import Ascenso.sytem.common.enums.CashSource;
import Ascenso.sytem.common.enums.CashTransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashTransactionResponseDto {

    private String id;
    private String transactionNumber;
    private CashTransactionType transactionType;
    private CashSource source;
    private BigDecimal amount;
    private Boolean cashIn;
    private String reason;
    private String referenceNumber;
    private String performedBy;
    private String sessionId;
    private LocalDateTime createdAt;
}
