package Ascenso.sytem.auth.service;

import Ascenso.sytem.auth.dto.*;
import Ascenso.sytem.common.exception.ResourceNotFoundException;
import Ascenso.sytem.common.utils.PhoneNumberUtils;
import Ascenso.sytem.security.jwt.JwtService;
import Ascenso.sytem.security.service.CustomUserDetails;
import Ascenso.sytem.user.dto.UserResponseDto;
import Ascenso.sytem.user.entity.Role;
import Ascenso.sytem.user.entity.User;
import Ascenso.sytem.user.repository.RoleRepository;
import Ascenso.sytem.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImplementation implements AuthServiceContract {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    // Simple refresh token storage (in production, use Redis)
    private final ConcurrentHashMap<String, String> refreshTokens = new ConcurrentHashMap<>();
    
    // Simple OTP storage (in production, use Redis)
    private final ConcurrentHashMap<String, String> otpStorage = new ConcurrentHashMap<>();

    @Override
    public AuthResponseDto login(LoginRequestDto requestDto) {
        String phoneNumber = PhoneNumberUtils.normalize(requestDto.getPhoneNumber());
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        phoneNumber,
                        requestDto.getPassword()
                )
        );

        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        User user = principal.getUser();

        return buildAuthResponse(user);
    }

    @Override
    public AuthResponseDto register(RegisterRequestDto requestDto) {
        String phoneNumber = PhoneNumberUtils.normalize(requestDto.getPhoneNumber());
        
        if (userRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new IllegalArgumentException("Phone number already registered");
        }
        
        User user = User.builder()
                .fullName(requestDto.getFullName())
                .phoneNumber(phoneNumber)
                .passwordHash(passwordEncoder.encode(requestDto.getPassword()))
                .enabled(true)
                .build();
        
        Role defaultRole = roleRepository.findByName("STAFF")
                .orElseGet(() -> roleRepository.save(Role.builder()
                        .name("STAFF")
                        .build()));
        user.setRoles(Set.of(defaultRole));
        
        User saved = userRepository.save(user);
        log.info("New user registered: {}", saved.getPhoneNumber());
        
        return buildAuthResponse(saved);
    }

    @Override
    public AuthResponseDto refreshToken(RefreshTokenRequestDto requestDto) {
        String token = requestDto.getRefreshToken();
        
        // Validate refresh token format (simple check)
        if (token == null || !token.startsWith("refresh_")) {
            throw new IllegalArgumentException("Invalid refresh token");
        }
        
        String phoneNumber = token.replace("refresh_", "");
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        return buildAuthResponse(user);
    }

    @Override
    public void logout(String phoneNumber) {
        // Clear refresh token
        refreshTokens.remove(phoneNumber);
        log.info("User logged out: {}", phoneNumber);
    }

    @Override
    public void requestPasswordReset(String phoneNumber) {
        String normalizedPhone = PhoneNumberUtils.normalize(phoneNumber);
        
        userRepository.findByPhoneNumber(normalizedPhone)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        // Generate simple OTP
        String otp = String.format("%06d", (int) (Math.random() * 999999));
        otpStorage.put(normalizedPhone, otp);
        
        log.info("OTP for {}: {}", normalizedPhone, otp);
    }

    @Override
    public void resetPassword(PasswordResetRequestDto requestDto) {
        String phoneNumber = PhoneNumberUtils.normalize(requestDto.getPhoneNumber());
        
        String storedOtp = otpStorage.get(phoneNumber);
        if (storedOtp == null || !storedOtp.equals(requestDto.getOtp())) {
            throw new IllegalArgumentException("Invalid OTP");
        }
        
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        user.setPasswordHash(passwordEncoder.encode(requestDto.getNewPassword()));
        userRepository.save(user);
        
        otpStorage.remove(phoneNumber);
        log.info("Password reset for: {}", phoneNumber);
    }

    private AuthResponseDto buildAuthResponse(User user) {
        Set<String> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());


        Set<String> permissions = user.getRoles()
                .stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(p -> p.getName())
                .collect(Collectors.toSet());

        java.util.HashMap<String, Object> claims = new java.util.HashMap<>();
        claims.put("userId", user.getId());
        claims.put("roles", roles);
        claims.put("permissions", permissions);

        String token = jwtService.generateToken(user.getPhoneNumber(), claims);
        
        // Store refresh token
        String refreshToken = "refresh_" + user.getPhoneNumber();
        refreshTokens.put(user.getPhoneNumber(), refreshToken);

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
                .refreshToken(refreshToken)
                .user(userResponse)
                .loginTime(LocalDateTime.now())
                .build();
    }
}
