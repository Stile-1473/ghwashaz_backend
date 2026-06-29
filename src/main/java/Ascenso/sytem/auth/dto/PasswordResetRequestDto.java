package Ascenso.sytem.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetRequestDto {

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "OTP is required")
    private String otp;

    @NotBlank(message = "New password is required")
    private String newPassword;
}
