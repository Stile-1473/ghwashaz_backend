package Ascenso.sytem.sale.dto;


import Ascenso.sytem.common.enums.PaymentMethod;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateSaleRequestDto {
    
    //optional
    private UUID customerId;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    @DecimalMin(value = "0.0", inclusive = false, message = "Discount must be greater than 0")
    private BigDecimal discount;

    @NotEmpty(message = "Sale must have at least one item")
    private List<SaleItemRequestDto> items;



}
