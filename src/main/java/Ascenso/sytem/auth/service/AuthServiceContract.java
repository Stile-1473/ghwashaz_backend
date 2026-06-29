package Ascenso.sytem.auth.service;

import Ascenso.sytem.auth.dto.*;

public interface AuthServiceContract {

    AuthResponseDto login(LoginRequestDto requestDto);

    AuthResponseDto register(RegisterRequestDto requestDto);

    AuthResponseDto refreshToken(RefreshTokenRequestDto requestDto);

    void logout(String phoneNumber);

    void requestPasswordReset(String phoneNumber);

    void resetPassword(PasswordResetRequestDto requestDto);
}
