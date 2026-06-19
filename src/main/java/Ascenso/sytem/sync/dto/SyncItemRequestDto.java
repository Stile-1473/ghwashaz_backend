package Ascenso.sytem.sync.dto;

import Ascenso.sytem.common.enums.SyncOperation;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SyncItemRequestDto {
    private  String entityType;
    private UUID entityId;
    private String payload;
    private SyncOperation operation;

}
