package Ascenso.sytem.auth.controller;

import Ascenso.sytem.auth.dto.*;
import Ascenso.sytem.auth.service.AuthServiceContract;
import Ascenso.sytem.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    
    private final AuthServiceContract authServiceContract;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDto>> login(@Valid @RequestBody LoginRequestDto requestDto) {
        AuthResponseDto response = authServiceContract.login(requestDto);
        return ResponseEntity.ok(
                ApiResponse.<AuthResponseDto>builder()
                        .success(true)
                        .message("Login successful")
                        .data(response)
                        .build()
        );
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponseDto>> register(@Valid @RequestBody RegisterRequestDto requestDto) {
        AuthResponseDto response = authServiceContract.register(requestDto);
        return ResponseEntity.ok(
                ApiResponse.<AuthResponseDto>builder()
                        .success(true)
                        .message("Registration successful")
                        .data(response)
                        .build()
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponseDto>> refreshToken(@Valid @RequestBody RefreshTokenRequestDto requestDto) {
        AuthResponseDto response = authServiceContract.refreshToken(requestDto);
        return ResponseEntity.ok(
                ApiResponse.<AuthResponseDto>builder()
                        .success(true)
                        .message("Token refreshed")
                        .data(response)
                        .build()
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestBody LogoutRequestDto requestDto) {
        authServiceContract.logout(requestDto.getPhoneNumber());
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Logout successful")
                        .build()
        );
    }

    @PostMapping("/password/reset-request")
    public ResponseEntity<ApiResponse<Void>> requestPasswordReset(@RequestBody PhoneNumberRequestDto requestDto) {
        authServiceContract.requestPasswordReset(requestDto.getPhoneNumber());
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("OTP sent to phone")
                        .build()
        );
    }

    @PostMapping("/password/reset")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@Valid @RequestBody PasswordResetRequestDto requestDto) {
        authServiceContract.resetPassword(requestDto);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Password reset successful")
                        .build()
        );
    }
}
