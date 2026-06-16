package Ascenso.sytem.user.dto;

import Ascenso.sytem.common.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequestDto {
    @NotBlank
    private String fullName;

    @NotBlank
    private String phoneNumber;

    @NotNull
    private Role role;
}
