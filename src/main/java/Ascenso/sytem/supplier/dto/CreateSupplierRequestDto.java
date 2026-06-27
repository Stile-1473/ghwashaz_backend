package Ascenso.sytem.supplier.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSupplierRequestDto {

    @NotBlank
    @Size(max = 150)
    private String companyName;

    @NotBlank
    @Size(max = 150)
    private String contactPerson;

    @NotBlank
    private String phoneNumber;

    private String alternativePhoneNumber;

    @Email
    private String email;

    @Size(max = 1000)
    private String address;

    private String city;

    private String country;

    @Size(max = 1000)
    private String notes;

}