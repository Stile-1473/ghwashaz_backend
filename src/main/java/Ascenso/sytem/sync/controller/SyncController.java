package Ascenso.sytem.sync.controller;

import Ascenso.sytem.common.enums.SyncStatus;
import Ascenso.sytem.common.response.ApiResponse;
import Ascenso.sytem.sync.dto.DownloadSyncResponseDto;
import Ascenso.sytem.sync.dto.PendingSyncResponseDto;
import Ascenso.sytem.sync.dto.SyncRequestDto;
import Ascenso.sytem.sync.dto.SyncResponseDto;
import Ascenso.sytem.sync.entity.SyncPendingItem;
import Ascenso.sytem.sync.repository.SyncStatusRepository;
import Ascenso.sytem.sync.service.SyncServiceContract;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Sync Controller - handles offline-first sync operations
 * 
 * Endpoints:
 * - POST /api/v1/sync/upload - Upload local changes
 * - GET /api/v1/sync/download?lastSync={timestamp} - Download server changes
 * - GET /api/v1/sync/pending - Get pending sync items
 */
@RestController
@RequestMapping("/api/v1/sync")
@RequiredArgsConstructor
public class SyncController {

    private final SyncServiceContract syncService;
    private final SyncStatusRepository syncStatusRepository;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<SyncResponseDto>> upload(
            @RequestBody SyncRequestDto requestDto) {
        SyncResponseDto response = syncService.upload(requestDto);
        return ResponseEntity.ok(ApiResponse.<SyncResponseDto>builder()
                .success(true)
                .message("Upload completed")
                .data(response)
                .build());
    }

    @GetMapping("/download")
    public ResponseEntity<ApiResponse<DownloadSyncResponseDto>> download(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
            LocalDateTime lastSync) {
        DownloadSyncResponseDto response = syncService.download(lastSync);
        return ResponseEntity.ok(ApiResponse.<DownloadSyncResponseDto>builder()
                .success(true)
                .data(response)
                .build());
    }

    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<PendingSyncResponseDto>> getPendingSync() {
        List<SyncPendingItem> pending = syncStatusRepository
                .findByStatusOrderByCreatedAtAsc(SyncStatus.PENDING);
        List<SyncPendingItem> failed = syncStatusRepository
                .findByStatusOrderByCreatedAtAsc(SyncStatus.FAILED);
        
        List<PendingSyncResponseDto.SyncItemDto> items = pending.stream()
                .map(s -> PendingSyncResponseDto.SyncItemDto.builder()
                        .id(s.getId())
                        .entityType(s.getEntityType())
                        .entityId(s.getEntityId())
                        .operation(s.getOperation())
                        .status(s.getStatus())
                        .createdAt(s.getCreatedAt())
                        .errorMessage(s.getErrorMessage())
                        .build())
                .toList();
        
        PendingSyncResponseDto response = PendingSyncResponseDto.builder()
                .totalPending(pending.size())
                .totalFailed(failed.size())
                .items(items)
                .build();
        
        return ResponseEntity.ok(ApiResponse.<PendingSyncResponseDto>builder()
                .success(true)
                .data(response)
                .build());
    }
}
