package Ascenso.sytem.cashier.validator;

import Ascenso.sytem.cashier.entity.CashierSession;
import Ascenso.sytem.cashier.repository.CashierSessionRepository;
import Ascenso.sytem.common.exception.BadRequestException;
import Ascenso.sytem.common.exception.ResourceNotFoundException;
import Ascenso.sytem.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CashierSessionValidator {

    private final CashierSessionRepository cashierSessionRepository;

    public CashierSession getValidatedSession(UUID id) {
        return cashierSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cashier session not found"));
    }

    public void validateActive(CashierSession session) {
        if (!session.getActive()) {
            throw new BadRequestException("Cashier session is not active");
        }
    }

    public CashierSession getOpenSession(User cashier) {
        return cashierSessionRepository.findByCashierIdAndActiveTrue(cashier.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No active session for cashier"));
    }
}
