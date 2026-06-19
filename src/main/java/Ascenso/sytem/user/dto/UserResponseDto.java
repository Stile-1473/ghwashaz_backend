package Ascenso.sytem.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
public class UserResponseDto {

    private UUID id;

    private String fullName;

    private String phoneNumber;

    private Set<String> permissions;

    private Boolean active;


    private LocalDateTime createdAt;

}
