package Ascenso.sytem.audit.dto;


import Ascenso.sytem.common.enums.AuditActionType;
import Ascenso.sytem.common.enums.AuditModule;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class AuditLogsResponseDto {

    private UUID id;

    private String phoneNumber;

    private String fullName;

    private AuditModule module;

    private AuditActionType action;

    private UUID entityId;

    private String description;

    private String ipAddress;

    private String device;

    private LocalDateTime createdAt;
}
