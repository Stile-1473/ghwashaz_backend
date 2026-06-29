package Ascenso.sytem.finance.service.impl;

import Ascenso.sytem.audit.service.AuditServiceContract;
import Ascenso.sytem.cashier.entity.CashierSession;
import Ascenso.sytem.cashier.repository.CashierSessionRepository;
import Ascenso.sytem.common.enums.AuditActionType;
import Ascenso.sytem.common.enums.AuditModule;
import Ascenso.sytem.common.exception.BadRequestException;
import Ascenso.sytem.common.exception.ResourceNotFoundException;
import Ascenso.sytem.common.response.PageResponse;
import Ascenso.sytem.finance.dto.*;
import Ascenso.sytem.finance.entity.CashTransaction;
import Ascenso.sytem.common.enums.CashTransactionType;
import Ascenso.sytem.finance.mapper.CashTransactionMapper;
import Ascenso.sytem.finance.repository.CashTransactionRepository;
import Ascenso.sytem.finance.service.CashTransactionService;
import Ascenso.sytem.finance.specification.CashTransactionSpecification;
import Ascenso.sytem.security.util.SecurityUtils;
import Ascenso.sytem.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CashTransactionServiceImpl implements CashTransactionService {

    private final CashTransactionRepository repository;
    private final CashierSessionRepository cashierSessionRepository;
    private final AuditServiceContract auditService;
    private final CashTransactionMapper mapper;

    @Override
    public CashTransactionResponseDto createTransaction(CashTransactionDto dto) {
        if (!dto.getCashIn()) {
            BigDecimal currentBalance = calculateSessionBalance();
            if (dto.getAmount().compareTo(currentBalance) > 0) {
                throw new BadRequestException(
                        "Withdrawal amount exceeds available balance: " + currentBalance);
            }
        }

        CashierSession session = cashierSessionRepository.findByActiveTrue()
                .orElseThrow(() -> new BadRequestException("No open cashier session"));

        User user = SecurityUtils.getCurrentUser().getUser();
        String transactionNumber = generateTransactionNumber(dto.getTransactionType());

        CashTransaction transaction = CashTransaction.builder()
                .transactionNumber(transactionNumber)
                .cashierSession(session)
                .performedBy(user)
                .transactionType(dto.getTransactionType())
                .source(dto.getSource())
                .amount(dto.getAmount())
                .cashIn(dto.getCashIn())
                .reason(dto.getReason())
                .referenceNumber(dto.getReferenceNumber())
                .build();

        CashTransaction saved = repository.save(transaction);

        try {
            auditService.log(AuditModule.FINANCE, AuditActionType.CREATE, saved.getId(),
                    dto.getTransactionType() + ": " + dto.getAmount());
        } catch (Exception e) {
            log.warn("Audit log failed: {}", e.getMessage());
        }

        log.info("Cash transaction: {} - {}", transactionNumber, dto.getAmount());
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CashTransactionResponseDto getTransaction(UUID id) {
        CashTransaction transaction = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        return mapper.toResponse(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CashTransactionResponseDto> getTransactions(
            CashTransactionQueryDto query, Pageable pageable) {
        Page<CashTransaction> page = repository.findAll(
                CashTransactionSpecification.filter(query), pageable);
        return PageResponse.from(page, mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public CashBalanceDto getCurrentBalance() {
        CashierSession session = cashierSessionRepository.findByActiveTrue()
                .orElseThrow(() -> new BadRequestException("No open cashier session"));

        BigDecimal sessionCashIn = repository.sumBySessionAndCashIn(session, true)
                .orElse(BigDecimal.ZERO);
        BigDecimal sessionCashOut = repository.sumBySessionAndCashIn(session, false)
                .orElse(BigDecimal.ZERO);

        BigDecimal currentBalance = session.getOpeningBalance()
                .add(sessionCashIn)
                .subtract(sessionCashOut);

        return CashBalanceDto.builder()
                .currentBalance(currentBalance)
                .sessionOpeningBalance(session.getOpeningBalance())
                .sessionCashIn(sessionCashIn)
                .sessionCashOut(sessionCashOut)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public CashSummaryDto getSummary(CashTransactionQueryDto query) {
        String txType = query != null && query.getTransactionType() != null 
                ? query.getTransactionType().name() : null;
        LocalDateTime start = query != null ? query.getStartDate() : null;
        LocalDateTime end = query != null ? query.getEndDate() : null;

        BigDecimal totalCashIn = repository.sumByFilter(txType, true, start, end).orElse(BigDecimal.ZERO);
        BigDecimal totalCashOut = repository.sumByFilter(txType, false, start, end).orElse(BigDecimal.ZERO);
        Long count = repository.countByFilter(txType, start, end);

        return CashSummaryDto.builder()
                .totalCashIn(totalCashIn)
                .totalCashOut(totalCashOut)
                .netBalance(totalCashIn.subtract(totalCashOut))
                .transactionCount(count)
                .build();
    }

    private BigDecimal calculateSessionBalance() {
        CashierSession session = cashierSessionRepository.findByActiveTrue().orElse(null);
        if (session == null) return BigDecimal.ZERO;

        BigDecimal cashIn = repository.sumBySessionAndCashIn(session, true).orElse(BigDecimal.ZERO);
        BigDecimal cashOut = repository.sumBySessionAndCashIn(session, false).orElse(BigDecimal.ZERO);

        return session.getOpeningBalance().add(cashIn).subtract(cashOut);
    }

    private String generateTransactionNumber(CashTransactionType type) {
        String prefix = type.name().substring(0, 3).toUpperCase();
        return prefix + "-" + System.currentTimeMillis();
    }
}
