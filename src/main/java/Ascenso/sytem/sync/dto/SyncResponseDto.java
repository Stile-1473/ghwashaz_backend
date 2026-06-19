package Ascenso.sytem.sync.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class SyncResponseDto {

    private Integer successful;

    private Integer failed;

    private List<SyncErrorResponse> errors;

    private LocalDateTime serveTime;
}
