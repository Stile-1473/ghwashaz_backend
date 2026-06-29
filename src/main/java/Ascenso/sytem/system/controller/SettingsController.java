package Ascenso.sytem.system.controller;

import Ascenso.sytem.common.constants.Permissions;
import Ascenso.sytem.common.response.ApiResponse;
import Ascenso.sytem.system.dto.SettingResponseDto;
import Ascenso.sytem.system.dto.UpdateSettingRequestDto;
import Ascenso.sytem.system.service.SettingServiceContract;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/settings")
@RequiredArgsConstructor
public class SettingsController {

    private final SettingServiceContract settingService;

    @GetMapping
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).REPORT_VIEW)")
    public ApiResponse<List<SettingResponseDto>> getAllSettings() {
        List<SettingResponseDto> settings = settingService.getAllSettings();
        return ApiResponse.<List<SettingResponseDto>>builder()
                .success(true)
                .data(settings)
                .build();
    }

    @PutMapping("/{key}")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).SETTINGS_MANAGE)")
    public ApiResponse<SettingResponseDto> updateSetting(
            @PathVariable String key,
            @Valid @RequestBody UpdateSettingRequestDto dto) {
        SettingResponseDto updated = settingService.updateSetting(key, dto.getValue());
        return ApiResponse.<SettingResponseDto>builder()
                .success(true)
                .message("Setting updated successfully")
                .data(updated)
                .build();
    }
}
