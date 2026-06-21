package Ascenso.sytem.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
public class UserSummaryResponseDto {

    private UUID id;

    private String fullName;

    private String phoneNumber;

    private Boolean enabled;

    private Set<String> roles;

}
