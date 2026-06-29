package Ascenso.sytem.finance.mapper;

import Ascenso.sytem.finance.dto.CashTransactionResponseDto;
import Ascenso.sytem.finance.entity.CashTransaction;
import org.springframework.stereotype.Component;

@Component
public class CashTransactionMapper {

    public CashTransactionResponseDto toResponse(CashTransaction entity) {
        if (entity == null) return null;
        
        return CashTransactionResponseDto.builder()
                .id(entity.getId().toString())
                .transactionNumber(entity.getTransactionNumber())
                .transactionType(entity.getTransactionType())
                .source(entity.getSource())
                .amount(entity.getAmount())
                .cashIn(entity.getCashIn())
                .reason(entity.getReason())
                .referenceNumber(entity.getReferenceNumber())
                .performedBy(entity.getPerformedBy().getFullName())
                .sessionId(entity.getCashierSession().getId().toString())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
