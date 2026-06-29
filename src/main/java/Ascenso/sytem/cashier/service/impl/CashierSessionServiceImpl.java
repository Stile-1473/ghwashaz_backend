package Ascenso.sytem.cashier.service.impl;

import Ascenso.sytem.audit.service.AuditServiceContract;
import Ascenso.sytem.cashier.dto.CashierSessionResponseDto;
import Ascenso.sytem.cashier.entity.CashierSession;
import Ascenso.sytem.cashier.repository.CashierSessionRepository;
import Ascenso.sytem.cashier.service.CashierSessionServiceContract;
import Ascenso.sytem.cashier.validator.CashierSessionValidator;
import Ascenso.sytem.common.enums.AuditActionType;
import Ascenso.sytem.common.enums.AuditModule;
import Ascenso.sytem.common.exception.BadRequestException;
import Ascenso.sytem.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CashierSessionServiceImpl implements CashierSessionServiceContract {

    private final CashierSessionRepository cashierSessionRepository;
    private final CashierSessionValidator cashierSessionValidator;
    private final AuditServiceContract auditService;

    @Override
    @Transactional
    public CashierSessionResponseDto openSession(BigDecimal openingBalance) {
        var currentUser = SecurityUtils.getCurrentUser().getUser();
        
        // Check if there already an active session
        cashierSessionRepository.findByCashierIdAndActiveTrue(currentUser.getId())
                .ifPresent(s -> {
                    throw new BadRequestException("Already have an active session");
                });
        
        CashierSession session = CashierSession.builder()
                .cashier(currentUser)
                .loginTime(LocalDateTime.now())
                .openingBalance(openingBalance != null ? openingBalance : BigDecimal.ZERO)
                .active(true)
                .build();
        
        CashierSession saved = cashierSessionRepository.save(session);
        
        try {
            auditService.log(AuditModule.CUSTOMER, AuditActionType.CREATE, saved.getId(),
                    "Opened cashier session");
        } catch (Exception e) {
            log.warn("Audit log failed: {}", e.getMessage());
        }
        
        log.info("Cashier session opened for: {}", currentUser.getFullName());
        
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public CashierSessionResponseDto closeSession(UUID sessionId, BigDecimal closingBalance) {

        var currentUser = SecurityUtils.getCurrentUser().getUser();
        CashierSession session = cashierSessionValidator.getValidatedSession(sessionId);
        
        // Security: Only the session owner can close their session
        if (!session.getCashier().getId().equals(currentUser.getId())) {
            throw new BadRequestException("You can only close your own session");
        }
        
        cashierSessionValidator.validateActive(session);
        
        session.setActive(false);
        session.setLogoutTime(LocalDateTime.now());
        session.setClosingBalance(closingBalance != null ? closingBalance : BigDecimal.ZERO);
        
        CashierSession saved = cashierSessionRepository.save(session);
        
        try {
            auditService.log(AuditModule.CUSTOMER, AuditActionType.UPDATE, saved.getId(),
                    "Closed cashier session");
        } catch (Exception e) {
            log.warn("Audit log failed: {}", e.getMessage());
        }
        
        log.info("Cashier session closed for: {}", session.getCashier().getFullName());
        
        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CashierSessionResponseDto getCurrentSession() {
        var currentUser = SecurityUtils.getCurrentUser().getUser();
        CashierSession session = cashierSessionValidator.getOpenSession(currentUser);
        return mapToResponse(session);
    }

    @Override
    @Transactional(readOnly = true)
    public CashierSessionResponseDto getSession(UUID sessionId) {
        CashierSession session = cashierSessionValidator.getValidatedSession(sessionId);
        return mapToResponse(session);
    }

    private CashierSessionResponseDto mapToResponse(CashierSession session) {
        return CashierSessionResponseDto.builder()
                .id(session.getId())
                .cashierName(session.getCashier().getFullName())
                .loginTime(session.getLoginTime())
                .logoutTime(session.getLogoutTime())
                .openingBalance(session.getOpeningBalance())
                .closingBalance(session.getClosingBalance())
                .active(session.getActive())
                .build();
    }
}
