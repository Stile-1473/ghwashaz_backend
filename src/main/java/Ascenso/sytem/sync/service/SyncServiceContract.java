package Ascenso.sytem.sync.service;

import Ascenso.sytem.sync.dto.DownloadSyncResponseDto;
import Ascenso.sytem.sync.dto.SyncRequestDto;
import Ascenso.sytem.sync.dto.SyncResponseDto;

import java.time.LocalDateTime;

public interface SyncServiceContract {
    SyncResponseDto upload(SyncRequestDto requestDto);

    DownloadSyncResponseDto download(LocalDateTime lastSync);
}
