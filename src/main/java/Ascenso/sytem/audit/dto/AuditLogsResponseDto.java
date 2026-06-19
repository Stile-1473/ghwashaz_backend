package Ascenso.sytem.audit.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class AuditLogsResponseDto {

    private UUID id;

    private String action;

    private String entityType;

    private UUID entityId;

    private String performedBy;

    private String performedAt;

    private String description;
}
