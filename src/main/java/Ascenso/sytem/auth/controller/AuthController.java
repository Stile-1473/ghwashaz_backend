package Ascenso.sytem.auth.controller;


import Ascenso.sytem.auth.dto.AuthResponseDto;
import Ascenso.sytem.auth.dto.LoginRequestDto;
import Ascenso.sytem.auth.service.AuthServiceContract;
import Ascenso.sytem.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthServiceContract authServiceContract;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDto>> login(@Valid @RequestBody LoginRequestDto requestDto){
        AuthResponseDto response = authServiceContract.login(requestDto);
        return  ResponseEntity.ok(
                ApiResponse.<AuthResponseDto>builder()
                        .success(true)
                        .message("Login successfully")
                        .data(response)
                        .build()
        );
    }
}

