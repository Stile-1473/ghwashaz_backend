package Ascenso.sytem.report.dto;

import Ascenso.sytem.common.enums.PaymentMethod;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class SalesReportResponseDto {

    private String saleNumber;

    private String customer;

    private String cashier;

    private BigDecimal totalAmount;

    private PaymentMethod paymentMethod;

    private LocalDateTime date;

}
