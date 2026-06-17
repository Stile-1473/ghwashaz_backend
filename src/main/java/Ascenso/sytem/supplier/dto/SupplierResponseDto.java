package Ascenso.sytem.supplier.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class SupplierResponseDto {

    private UUID id;

    private String name;

    private String contactPerson;

    private String phoneNumber;

    private String address;

    private Boolean active;

    private LocalDateTime createdAt;
}
