package Ascenso.sytem.supplier.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSupplierRequestDto {

    @NotBlank
    private String name;

    private String contactPerson;

    private String phoneNumber;

    private  String address;

    private  String notes;

}
