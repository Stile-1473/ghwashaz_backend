package Ascenso.sytem.finance.service;

import Ascenso.sytem.common.response.PageResponse;
import Ascenso.sytem.finance.dto.CreateExpenseRequestDto;
import Ascenso.sytem.finance.dto.ExpenseResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FinanceService {

    /**
     * Record a business expense
     */
    ExpenseResponseDto recordExpense(CreateExpenseRequestDto dto);

    /**
     * Get expense by ID
     */
    ExpenseResponseDto getExpense(UUID id);

    /**
     * List all expenses
     */
    PageResponse<ExpenseResponseDto> getExpenses(Pageable pageable);

    
}
