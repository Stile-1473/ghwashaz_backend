package Ascenso.sytem.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequestDto {
    //Users can change their own passwords
    //Owner can reset a cashier password
    //Passwords are always stored using BCRYPT

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;
}
