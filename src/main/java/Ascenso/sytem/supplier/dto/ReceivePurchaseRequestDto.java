package Ascenso.sytem.supplier.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReceivePurchaseRequestDto {


    @NotEmpty
    private List<ReceivedItemDto> items;

    private String notes;
}
