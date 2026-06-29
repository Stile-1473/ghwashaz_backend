package Ascenso.sytem.customer.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class CustomerStatisticsDto {
    private int totalPurchases;
    private BigDecimal totalSpent;
    private BigDecimal averageSale;
    private BigDecimal largestSale;
    private String lastPurchaseDate;
    private int returnsCount;
}
