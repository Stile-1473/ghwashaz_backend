package Ascenso.sytem.customer.dto;

import Ascenso.sytem.common.enums.CustomerType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCustomerRequestDto {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number")
    private String phoneNumber;

    @Email(message = "Invalid email format")
    private String email;

    @Size(max = 200, message = "Address too long")
    private String address;

    @NotNull
    private CustomerType customerType;
}
