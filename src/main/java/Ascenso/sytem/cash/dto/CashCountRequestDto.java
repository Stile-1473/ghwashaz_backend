package Ascenso.sytem.cash.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CashCountRequestDto {
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal countedCash;

    private String comment;
}
