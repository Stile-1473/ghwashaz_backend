package Ascenso.sytem.system.service.impl;

import Ascenso.sytem.audit.service.AuditServiceContract;
import Ascenso.sytem.common.enums.AuditActionType;
import Ascenso.sytem.common.enums.AuditModule;
import Ascenso.sytem.common.enums.SettingKey;
import Ascenso.sytem.security.util.SecurityUtils;
import Ascenso.sytem.system.dto.SettingResponseDto;
import Ascenso.sytem.system.entity.Setting;
import Ascenso.sytem.system.repository.SettingRepository;
import Ascenso.sytem.system.service.SettingServiceContract;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SettingServiceImpl implements SettingServiceContract {

    private final SettingRepository settingRepository;
    private final AuditServiceContract auditService;

    @Override
    public List<SettingResponseDto> getAllSettings() {
        return settingRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SettingResponseDto updateSetting(String key, String value) {
        SettingKey settingKey = SettingKey.valueOf(key);
        Setting setting = settingRepository.findBySettingsKey(settingKey.name())
                .orElseGet(() -> Setting.builder()
                        .settingsKey(settingKey)
                        .build());
        
        String oldValue = setting.getValue();
        setting.setValue(value);
        Setting saved = settingRepository.save(setting);
        
        try {
            String changedBy = SecurityUtils.getCurrentUser().getUser().getFullName();
            auditService.log(AuditModule.SETTINGS, AuditActionType.UPDATE, saved.getId(),
                    "Setting updated: " + key + " = " + value + " (was: " + oldValue + ") by " + changedBy);
        } catch (Exception e) {
            log.warn("Audit log failed for setting update: {}", e.getMessage());
        }
        
        log.info("Setting updated: {} = {}", key, value);
        return toDto(saved);
    }

    @Override
    public String getValue(String key) {
        return settingRepository.findBySettingsKey(key)
                .map(Setting::getValue)
                .orElse(null);
    }

    private SettingResponseDto toDto(Setting setting) {
        return SettingResponseDto.builder()
                .settingKey(setting.getSettingsKey().name())
                .value(setting.getValue())
                .build();
    }
}
