package Ascenso.sytem.sync.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SyncErrorResponse {

    private String message;

    private String entity;

    private String field;

    private Object failedValue;
}
