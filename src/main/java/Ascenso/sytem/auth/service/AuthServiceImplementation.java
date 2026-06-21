package Ascenso.sytem.auth.service;

import Ascenso.sytem.auth.dto.AuthResponseDto;
import Ascenso.sytem.auth.dto.LoginRequestDto;
import Ascenso.sytem.security.jwt.JwtService;
import Ascenso.sytem.security.service.CustomUserDetails;
import Ascenso.sytem.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements  AuthServiceContract{

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Override
    public AuthResponseDto login(LoginRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getPhoneNumber(),
                        requestDto.getPassword()
                )

        );

        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

        var user = principal.getUser();

        Set<String> roles = user.getRoles()
                .stream()

                .map(role -> role.getName())

                .collect(Collectors.toSet())
                ;

        Set<String> permissions = user.getRoles()
                .stream()

                .flatMap(role -> role.getPermissions().stream())

                .map(permission -> permission.getName())

                .collect(Collectors.toSet());

        HashMap<String,Object> claims = new HashMap<>();

        claims.put("userId",user.getId());
        claims.put("roles", roles);
        claims.put("permissions", permissions);

        String token = jwtService.generateToken(
                user.getPhoneNumber(),
                claims
        );

        UserResponseDto userResponse = UserResponseDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .permissions(permissions)
                .enabled(user.getEnabled())
                .createdAt(user.getCreatedAt())
                .build();

        return AuthResponseDto.builder()
                .token(token)
                .user(userResponse)
                .loginTime(LocalDateTime.now())
                .build();
    }
}
