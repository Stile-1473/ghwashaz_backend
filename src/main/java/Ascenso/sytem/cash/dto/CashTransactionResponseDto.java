package Ascenso.sytem.cash.dto;

import Ascenso.sytem.common.enums.CashTransactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
public class CashTransactionResponseDto {

   private UUID cashDrawerId;

    private CashTransactionType type;

    private BigDecimal amount;

    private String reason;

    private UUID referenceId;

    private String performedBy;
}
