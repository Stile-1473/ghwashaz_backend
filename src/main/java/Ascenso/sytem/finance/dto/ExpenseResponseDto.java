package Ascenso.sytem.finance.dto;

import Ascenso.sytem.common.enums.ExpenseCategory;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class ExpenseResponseDto {
    private UUID id;
    private String description;
    private ExpenseCategory category;
    private BigDecimal amount;
    private String supplierName;
    private String receiptNumber;
    private String recordedBy;
    private LocalDateTime createdAt;
}
