package Ascenso.sytem.sync.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SyncRequestDto {

    private List<SyncItemRequestDto> items;
}
