package Ascenso.sytem.auth.dto;

import Ascenso.sytem.user.dto.UserResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AuthResponseDto {

    private String token;
    
    private String refreshToken;
    
    private UserResponseDto user;

    private LocalDateTime loginTime;
}
