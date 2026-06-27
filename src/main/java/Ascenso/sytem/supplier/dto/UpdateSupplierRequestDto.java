package Ascenso.sytem.supplier.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSupplierRequestDto {

    @NotBlank
    private String companyName;

    @NotBlank
    private String contactPerson;

    @NotBlank
    private String phoneNumber;

    private String alternativePhoneNumber;

    @Email
    private String email;

    private String address;

    private String city;

    private String country;

    private String notes;

}
