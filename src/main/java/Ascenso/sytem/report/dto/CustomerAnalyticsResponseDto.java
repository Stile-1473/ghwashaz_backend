package Ascenso.sytem.report.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class CustomerAnalyticsResponseDto {

    private String customerName;

    private Integer numberOfOrders;

    private BigDecimal totalSp;

    private BigDecimal averageOderValue;
}
