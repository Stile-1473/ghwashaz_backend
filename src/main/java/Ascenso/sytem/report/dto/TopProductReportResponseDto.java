package Ascenso.sytem.report.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class TopProductReportResponseDto {

    private String productName;

    private Integer quantitySold;

    private BigDecimal revenueGenerated;
}
