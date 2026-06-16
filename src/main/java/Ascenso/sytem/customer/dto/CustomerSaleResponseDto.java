package Ascenso.sytem.customer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class CustomerSaleResponseDto {
    private UUID saleId;

    private String saleNumber;

    private BigDecimal amount;

    private LocalDateTime saleDate;
}
