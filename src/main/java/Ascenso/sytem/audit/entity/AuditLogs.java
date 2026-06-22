package Ascenso.sytem.audit.entity;

import Ascenso.sytem.common.enums.AuditActionType;
import Ascenso.sytem.common.enums.AuditModule;
import Ascenso.sytem.user.entity.User;
import Ascenso.sytem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(
        name = "audit_logs",
        indexes = {
                @Index(name = "idx_audit_user",columnList = "user_id"),
                @Index(name = "idx_audit_date",columnList = "created_at")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogs extends BaseEntity {

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String fullName;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 40)
    private AuditModule module;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private AuditActionType action;

    @Column(nullable = false)
    private UUID entityId;

    @Column(length = 1000)
    private String description;

    private String ipAddress;

    private String device;


}
