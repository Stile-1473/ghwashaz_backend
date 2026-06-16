package Ascenso.sytem.user.dto;

import Ascenso.sytem.common.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequestDto {

    //phone number must be unique
    //password must satisfy security rules
    //Role must always be owner or Cashier

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;


    @NotNull(message = "Role is required")
    private Role role;

    @NotBlank(message = "Password is required")
    private String password;

}
