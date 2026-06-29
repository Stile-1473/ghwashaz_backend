package Ascenso.sytem.sale.refund.dto;

import Ascenso.sytem.sale.refund.entity.RefundMethod;
import Ascenso.sytem.sale.refund.entity.ReturnType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CreateReturnRequestDto {

    @NotNull
    private UUID originalSaleId;

    @NotNull
    private ReturnType returnType;

    @NotNull
    private RefundMethod refundMethod;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal refundAmount;

    private String reason;

}
