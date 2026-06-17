package Ascenso.sytem.report.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.service.annotation.GetExchange;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class InventoryValueResponseDto {

    private String productName;
    private Integer quantity;
    private BigDecimal costPrice;
    private BigDecimal totalValue;
}
