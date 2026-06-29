package Ascenso.sytem.cashier.service;

import Ascenso.sytem.cashier.dto.CashierSessionResponseDto;

import java.math.BigDecimal;
import java.util.UUID;

public interface CashierSessionServiceContract {

    CashierSessionResponseDto openSession(BigDecimal openingBalance);

    CashierSessionResponseDto closeSession(UUID sessionId, BigDecimal closingBalance);

    CashierSessionResponseDto getCurrentSession();

    CashierSessionResponseDto getSession(UUID sessionId);

}
