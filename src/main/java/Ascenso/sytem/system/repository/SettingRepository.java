package Ascenso.sytem.system.repository;

import Ascenso.sytem.system.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettingRepository extends JpaRepository<Setting, java.util.UUID> {
    Optional<Setting> findBySettingsKey(String key);
}
