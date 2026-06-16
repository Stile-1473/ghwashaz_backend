package Ascenso.sytem.auth.service;


import Ascenso.sytem.auth.dto.LoginRequestDto;
import Ascenso.sytem.user.dto.UserResponseDto;

public interface AuthServiceContract {

    UserResponseDto login(LoginRequestDto requestDto);
}
