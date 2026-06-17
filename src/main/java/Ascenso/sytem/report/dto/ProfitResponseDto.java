package Ascenso.sytem.report.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ProfitResponseDto {

    private BigDecimal revenue;

    private BigDecimal cost;

    private BigDecimal profit;

    private BigDecimal profitMargin;

}
