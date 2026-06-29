package Ascenso.sytem.sync.entity;

import Ascenso.sytem.common.entity.BaseEntity;
import Ascenso.sytem.common.enums.SyncOperation;
import Ascenso.sytem.common.enums.SyncStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * SyncStatus Entity - tracks pending sync items for offline-first operations
 * 
 * Fields:
 * - entityType: Type of entity (SALE, PRODUCT, INVENTORY, etc)
 * - entityId: UUID of the entity
 * - operation: CREATE, UPDATE, DELETE
 * - status: PENDING, SYNCED, FAILED
 * - errorMessage: Error if failed
 */
@Entity
@Table(name = "sync_status")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyncPendingItem extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SyncOperation operation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private SyncStatus status = SyncStatus.PENDING;

    @Column(nullable = false)
    private String entityType;

    @Column(nullable = false)
    private UUID entityId;

    @Column(columnDefinition = "TEXT")
    private String payload;

    private Integer retryCount;

    private String errorMessage;

    private LocalDateTime syncedAt;
}
