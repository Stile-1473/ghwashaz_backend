package Ascenso.sytem.system.service;

import Ascenso.sytem.system.dto.SettingResponseDto;

import java.util.List;

public interface SettingServiceContract {

    List<SettingResponseDto> getAllSettings();

    SettingResponseDto updateSetting(String key, String value);

    String getValue(String key);

}
