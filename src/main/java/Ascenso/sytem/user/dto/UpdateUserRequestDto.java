package Ascenso.sytem.user.dto;

import Ascenso.sytem.user.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class UpdateUserRequestDto {
    @NotBlank
    private String fullName;

    @NotBlank
    private String phoneNumber;

   @NotEmpty(message = "At least one role is required")
    private Set<UUID> roleIds;
}
