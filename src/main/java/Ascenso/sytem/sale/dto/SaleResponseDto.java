package Ascenso.sytem.sale.dto;

import Ascenso.sytem.common.enums.PaymentMethod;
import Ascenso.sytem.common.enums.SaleStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class SaleResponseDto {

    private UUID id;

    private String saleNumber;
    private String cashierName;
    private String customerName;
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal totalAmount;
    private PaymentMethod paymentMethod;
    private SaleStatus saleStatus;
    private LocalDateTime saleDate;
}
