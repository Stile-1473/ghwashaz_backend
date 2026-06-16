package Ascenso.sytem.customer.dto;

import Ascenso.sytem.common.enums.CustomerType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCustomerRequestDto {

    private String fullName;

    @Pattern(
            regexp="^(\\+263|[0-9]{9}$",
            message = "Invalid phone number"
    )
    private String phoneNumber;

    private String address;


    @NotNull(message = "Customer type is required")
    private CustomerType customerType;

    private String notes;



}
