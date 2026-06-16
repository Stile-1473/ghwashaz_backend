package Ascenso.sytem.auth.dto;

import Ascenso.sytem.user.dto.UserResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResponseDto {

    private String token;

    private UserResponseDto userResponseDto;
}
