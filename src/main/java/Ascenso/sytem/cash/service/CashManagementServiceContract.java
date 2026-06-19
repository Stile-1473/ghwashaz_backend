package Ascenso.sytem.cash.service;


import Ascenso.sytem.cash.dto.CashCountRequestDto;
import Ascenso.sytem.cash.dto.CashDrawerResponseDto;
import Ascenso.sytem.cash.dto.CashTransactionResponseDto;
import Ascenso.sytem.cash.dto.WithdrawCashRequestDto;
import Ascenso.sytem.common.enums.CashTransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface CashManagementServiceContract {

    CashDrawerResponseDto openDrawer(BigDecimal openingBalance);

    CashDrawerResponseDto getCurrentDrawer();

    void withdrawCash(WithdrawCashRequestDto requestDto);

    void depositCash(BigDecimal amount,String reason);

    void recordExpense(BigDecimal amount,String reason);

    void closeDrawer(CashCountRequestDto requestDto);

    Page<CashTransactionResponseDto> getTransactions(
            CashTransactionType type,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );
}
