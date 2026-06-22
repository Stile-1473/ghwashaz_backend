package Ascenso.sytem.audit.repo;

import Ascenso.sytem.audit.entity.AuditLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface AuditRepository extends JpaRepository<AuditLogs, UUID>,
        JpaSpecificationExecutor<AuditLogs>
{





}
