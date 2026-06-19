package Ascenso.sytem.cash.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class CashDrawerResponseDto {

    private BigDecimal openingBalance;

    private BigDecimal currentBalance;

    private String cashier;

    private Boolean open;

    private LocalDate businessDate;


}
