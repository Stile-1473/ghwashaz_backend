package Ascenso.sytem.sale.dto;

import Ascenso.sytem.common.enums.PaymentMethod;
import Ascenso.sytem.common.enums.SaleStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class SaleResponseDto {


    private UUID id;
    private String saleNumber;
    private String cashierName;
    private String customerName;
    private SaleStatus status;
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal tax;
    private BigDecimal total;
    private LocalDateTime createdAt;

}
