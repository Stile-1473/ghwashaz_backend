package Ascenso.sytem.system.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SettingResponseDto {

    private String settingKey;

    private String value;

    private String description;
}
