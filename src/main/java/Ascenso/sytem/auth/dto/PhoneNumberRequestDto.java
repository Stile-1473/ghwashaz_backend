package Ascenso.sytem.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumberRequestDto {

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
}
