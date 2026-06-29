package Ascenso.sytem.cashier.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class CashierSessionResponseDto {
    private UUID id;
    private String cashierName;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;
    private BigDecimal openingBalance;
    private BigDecimal closingBalance;
    private Boolean active;
}
