package Ascenso.sytem.sale.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ReceiptResponseDto {

    private String saleNumber;

    private String shopName;

    private String shopPhone;

    private String cashier;

    private String customer;

    private List<SaleItemResponseDto> items;

    private BigDecimal subtotal;

    private BigDecimal discount;

    private BigDecimal total;

    private LocalDateTime saleDate;

    private String thankYouMessage;
}
