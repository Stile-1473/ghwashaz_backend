package Ascenso.sytem.auth.service;


import Ascenso.sytem.auth.dto.AuthResponseDto;
import Ascenso.sytem.auth.dto.LoginRequestDto;

public interface AuthServiceContract {

    AuthResponseDto login(LoginRequestDto requestDto);
}
