package Ascenso.sytem.system.service;

import Ascenso.sytem.system.dto.SettingResponseDto;
import Ascenso.sytem.system.dto.UpdateSettingRequestDto;

import java.util.List;

public interface SettingServiceContract {

    List<SettingResponseDto> getAllSettings();

    SettingResponseDto updateSetting(UpdateSettingRequestDto requestDto);

    String getValue(String key);


}
