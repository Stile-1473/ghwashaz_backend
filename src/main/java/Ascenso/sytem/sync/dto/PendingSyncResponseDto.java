package Ascenso.sytem.sync.dto;

import Ascenso.sytem.common.enums.SyncOperation;
import Ascenso.sytem.common.enums.SyncStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PendingSyncResponseDto {

    private long totalPending;
    private long totalFailed;
    private List<SyncItemDto> items;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SyncItemDto {
        private UUID id;
        private String entityType;
        private UUID entityId;
        private SyncOperation operation;
        private SyncStatus status;
        private LocalDateTime createdAt;
        private String errorMessage;
    }
}
