package Ascenso.sytem.supplier.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreatePurchaseRequestDto {

    @NotNull
    private UUID supplierId;


    @NotEmpty
    private List<PurchaseItemRequestDto> items;

    private String notes;
}
