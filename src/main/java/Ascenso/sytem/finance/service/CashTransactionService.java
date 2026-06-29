package Ascenso.sytem.finance.service;

import Ascenso.sytem.common.response.PageResponse;
import Ascenso.sytem.finance.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CashTransactionService {

    CashTransactionResponseDto createTransaction(CashTransactionDto dto);

    CashTransactionResponseDto getTransaction(UUID id);

    PageResponse<CashTransactionResponseDto> getTransactions(CashTransactionQueryDto query, Pageable pageable);

    CashBalanceDto getCurrentBalance();

    CashSummaryDto getSummary(CashTransactionQueryDto query);
}
