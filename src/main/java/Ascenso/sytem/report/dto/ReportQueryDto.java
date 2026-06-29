package Ascenso.sytem.report.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportQueryDto {

    private LocalDate startDate;
    private LocalDate endDate;
    private String period;
}
