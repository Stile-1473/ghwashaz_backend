package Ascenso.sytem.sale.refund.dto;

import Ascenso.sytem.sale.refund.entity.RefundMethod;
import Ascenso.sytem.sale.refund.entity.ReturnType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class ReturnResponseDto {
    private UUID id;
    private String originalSaleNumber;
    private String processedBy;
    private ReturnType returnType;
    private RefundMethod refundMethod;
    private BigDecimal refundAmount;
    private String reason;
    private String referenceNumber;
    private LocalDateTime createdAt;
}
