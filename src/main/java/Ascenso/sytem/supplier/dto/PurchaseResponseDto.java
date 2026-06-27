package Ascenso.sytem.supplier.dto;

import Ascenso.sytem.common.enums.PurchaseStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Getter
@Builder
public class PurchaseResponseDto {

    private UUID id;

    private String purchaseNumber;

    private UUID supplierId;

    private String supplierName;

    private PurchaseStatus status;

    private BigDecimal subtotal;

    private BigDecimal discount;

    private BigDecimal tax;

    private BigDecimal total;

    private String notes;

    private List<PurchaseItemResponseDto> items;

    private LocalDateTime createdAt;

}