package Ascenso.sytem.supplier.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReceivePurchaseRequestDto {


    @NotEmpty
    private List<ReceivedItenDto> items;

    private String notes;
}
