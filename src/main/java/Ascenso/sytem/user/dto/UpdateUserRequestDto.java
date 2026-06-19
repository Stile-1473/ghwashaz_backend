package Ascenso.sytem.user.dto;

import Ascenso.sytem.user.entity.Roles;
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
    private Roles role;
}
