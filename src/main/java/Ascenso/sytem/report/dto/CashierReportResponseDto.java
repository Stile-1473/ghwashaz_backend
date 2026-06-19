package Ascenso.sytem.report.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class CashierReportResponseDto {

    private String CashierName;

    private Integer totalSales;

    private BigDecimal revenue;

    private BigDecimal profitGenerated;

    private Integer customerServed;
}
