package Ascenso.sytem.system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSettingRequestDto {

    @NotBlank
    private String value;

}
