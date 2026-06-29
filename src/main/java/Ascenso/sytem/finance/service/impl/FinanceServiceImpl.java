package Ascenso.sytem.finance.service.impl;

import Ascenso.sytem.audit.service.AuditServiceContract;
import Ascenso.sytem.cashier.entity.CashierSession;
import Ascenso.sytem.cashier.repository.CashierSessionRepository;
import Ascenso.sytem.common.enums.AuditActionType;
import Ascenso.sytem.common.enums.AuditModule;
import Ascenso.sytem.common.response.PageResponse;
import Ascenso.sytem.finance.dto.CreateExpenseRequestDto;
import Ascenso.sytem.finance.dto.ExpenseResponseDto;
import Ascenso.sytem.finance.entity.CashTransaction;
import Ascenso.sytem.common.enums.CashTransactionType;
import Ascenso.sytem.finance.entity.Expense;
import Ascenso.sytem.finance.mapper.ExpenseMapper;
import Ascenso.sytem.finance.repository.CashTransactionRepository;
import Ascenso.sytem.finance.repository.ExpenseRepository;
import Ascenso.sytem.finance.service.FinanceService;
import Ascenso.sytem.finance.specification.FinanceSpecification;
import Ascenso.sytem.finance.validator.FinanceValidator;
import Ascenso.sytem.security.util.SecurityUtils;
import Ascenso.sytem.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FinanceServiceImpl implements FinanceService {

    private final ExpenseRepository expenseRepository;
    private final CashTransactionRepository cashTransactionRepository;
    private final CashierSessionRepository cashierSessionRepository;
    private final AuditServiceContract auditService;
    private final FinanceValidator financeValidator;
    private final ExpenseMapper expenseMapper;

    @Override
    public ExpenseResponseDto recordExpense(CreateExpenseRequestDto dto) {

        financeValidator.validateDescription(dto.getDescription());

        financeValidator.validateExpenseAmount(dto.getAmount());
        
        User user = SecurityUtils.getCurrentUser().getUser();
        
        Expense expense = Expense.builder()
                .description(dto.getDescription())
                .category(dto.getCategory())
                .amount(dto.getAmount())
                .recordedBy(user)
                .supplierName(dto.getSupplierName())
                .receiptNumber(dto.getReceiptNumber())
                .build();
        
        Expense saved = expenseRepository.save(expense);
        
        if (dto.getPaidFromCash() != null && dto.getPaidFromCash()) {
            createCashTransactionFromExpense(saved, user);
        }
        
        try {
            auditService.log(AuditModule.FINANCE, AuditActionType.CREATE, saved.getId(), 
                    "Recorded expense: " + saved.getDescription());
        } catch (Exception e) {
            log.warn("Audit log failed for expense: {}", e.getMessage());
        }
        
        log.info("Expense recorded: {} - {}", saved.getDescription(), saved.getAmount());
        
        return expenseMapper.toResponse(saved);
    }

    private void createCashTransactionFromExpense(Expense expense, User user) {

        try {
            CashierSession session = cashierSessionRepository.findByActiveTrue().orElse(null);
            if (session == null) {
                log.warn("No open cashier session - cash transaction not created");
                return;
            }

            BigDecimal currentCashIn = cashTransactionRepository.sumBySessionAndCashIn(session, true)
                    .orElse(BigDecimal.ZERO);

            BigDecimal currentCashOut = cashTransactionRepository.sumBySessionAndCashIn(session, false)
                    .orElse(BigDecimal.ZERO);
            BigDecimal balance = session.getOpeningBalance().add(currentCashIn).subtract(currentCashOut);
            
            if (expense.getAmount().compareTo(balance) > 0) {
                log.warn("Expense amount exceeds cash balance - transaction not created");
                return;
            }

            String transactionNumber = "EXP-" + System.currentTimeMillis();
            CashTransaction cashTx = CashTransaction.builder()
                    .transactionNumber(transactionNumber)
                    .cashierSession(session)
                    .performedBy(user)
                    .transactionType(CashTransactionType.EXPENSE)
                    .amount(expense.getAmount())
                    .cashIn(false)
                    .reason("Expense: " + expense.getDescription())
                    .referenceNumber(expense.getReceiptNumber())
                    .build();
            
            cashTransactionRepository.save(cashTx);
            log.info("Cash transaction created for expense: {}", transactionNumber);
            
        } catch (Exception e) {
            log.warn("Failed to create cash transaction: {}", e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ExpenseResponseDto getExpense(UUID id) {
        Expense expense = financeValidator.getValidatedExpense(id);
        return expenseMapper.toResponse(expense);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ExpenseResponseDto> getExpenses(Pageable pageable) {
        Page<Expense> page = expenseRepository.findAll(
                FinanceSpecification.expenseSearch(null, null, null, null), pageable);
        return PageResponse.from(page, expenseMapper::toResponse);
    }

}
