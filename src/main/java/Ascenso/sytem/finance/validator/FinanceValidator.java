package Ascenso.sytem.finance.validator;

import Ascenso.sytem.common.exception.BadRequestException;
import Ascenso.sytem.common.exception.ResourceNotFoundException;
import Ascenso.sytem.finance.entity.Expense;
import Ascenso.sytem.finance.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class FinanceValidator {

    private final ExpenseRepository expenseRepository;

    /**
     * Get and validate expense by ID
     */
    public Expense getValidatedExpense(UUID id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));
    }

    /**
     * Validate expense amount is within acceptable range
     */
    public void validateExpenseAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Expense amount must be greater than zero");
        }
        if (amount.compareTo(new BigDecimal("1000000")) > 0) {
            throw new BadRequestException("Expense amount exceeds maximum allowed");
        }
    }

    /**
     * Validate expense description
     */
    public void validateDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new BadRequestException("Description is required");
        }
        if (description.length() < 3) {
            throw new BadRequestException("Description must be at least 3 characters");
        }
    }

}
