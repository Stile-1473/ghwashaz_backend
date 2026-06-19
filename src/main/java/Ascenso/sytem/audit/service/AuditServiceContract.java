package Ascenso.sytem.audit.service;

import Ascenso.sytem.audit.dto.AuditLogsResponseDto;
import Ascenso.sytem.common.enums.AuditActionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.UUID;

public interface AuditServiceContract {

    void log(
            AuditActionType auditActionType,
            String entityType,
            UUID entityId,
            String description
    );

    Page<AuditLogsResponseDto> getLogs(
            String entityType,
            UUID userId,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );




}
