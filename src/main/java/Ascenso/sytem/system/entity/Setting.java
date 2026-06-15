package Ascenso.sytem.system.entity;

import Ascenso.sytem.common.entity.BaseEntity;
import Ascenso.sytem.common.enums.SettingKey;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "settings",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "setting_key")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Setting extends BaseEntity {


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private SettingKey settingsKey;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String value;


    @Column(columnDefinition = "TEXT")
    private String description;
}