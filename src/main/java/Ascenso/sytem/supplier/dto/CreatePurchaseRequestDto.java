package Ascenso.sytem.supplier.dto;

import Ascenso.sytem.supplier.dto.PurchaseItemRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
public class CreatePurchaseRequestDto {

    @NotNull
    private UUID supplierId;

    @Builder.Default
    private BigDecimal discount = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal tax = BigDecimal.ZERO;

    private String notes;

    @Valid
    @NotEmpty(message = "Purchase must contain at least one item")
    private List<PurchaseItemRequestDto> items;

}