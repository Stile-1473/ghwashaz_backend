package Ascenso.sytem.user.dto;

import Ascenso.sytem.common.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class UserResponseDto {

    private UUID id;

    private String fullName;

    private String phoneNumber;

    private Role role;

    private Boolean active;

    private LocalDateTime createdAt;

}
