package Ascenso.sytem.audit.service;

import Ascenso.sytem.audit.dto.AuditLogsResponseDto;
import Ascenso.sytem.common.enums.AuditActionType;
import Ascenso.sytem.common.enums.AuditModule;
import Ascenso.sytem.common.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.UUID;

public interface AuditServiceContract {

    void log(
       AuditModule module,
       AuditActionType action,
       UUID entityId,
       String description



    );

    PageResponse<AuditLogsResponseDto> getLogs(
            AuditModule module,
            AuditActionType action,
            String search,
            Pageable pageable
    );




}
