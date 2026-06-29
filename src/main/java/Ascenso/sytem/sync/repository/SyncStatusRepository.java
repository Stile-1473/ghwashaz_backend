package Ascenso.sytem.sync.repository;

import Ascenso.sytem.common.enums.SyncStatus;
import Ascenso.sytem.sync.entity.SyncPendingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SyncStatusRepository extends JpaRepository<SyncPendingItem, UUID> {

    List<SyncPendingItem> findByStatus(Ascenso.sytem.common.enums.SyncStatus status);


    List<SyncPendingItem> findByStatusOrderByCreatedAtAsc(Ascenso.sytem.common.enums.SyncStatus status);

    long countByStatus(Ascenso.sytem.common.enums.SyncStatus status);
}
