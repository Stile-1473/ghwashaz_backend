package Ascenso.sytem.finance.dto;

import Ascenso.sytem.common.enums.CashSource;
import Ascenso.sytem.common.enums.CashTransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashTransactionDto {

    @NotNull(message = "Transaction type is required")
    private CashTransactionType transactionType;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;


    @NotNull(message = "Cash in/out is required")
    private Boolean cashIn;

    @NotBlank(message = "Reason is required")
    private String reason;

    private CashSource source;

    private String referenceNumber;
}
