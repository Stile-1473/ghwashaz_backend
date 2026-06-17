package Ascenso.sytem.supplier.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class PurchaseResponseDto {

    private UUID id;

    private String purchaseNumber;

    private String supplierName;

    private LocalDateTime createdAt;

    private String createdBy;


}
