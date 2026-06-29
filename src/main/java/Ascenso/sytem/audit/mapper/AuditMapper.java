package Ascenso.sytem.audit.mapper;

import Ascenso.sytem.audit.dto.AuditLogsResponseDto;
import Ascenso.sytem.audit.entity.AuditLogs;
import Ascenso.sytem.common.mapper.MapperConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = MapperConfiguration.class)
public interface AuditMapper {

    AuditLogsResponseDto toResponse(AuditLogs auditLogs);
}
