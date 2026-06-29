package Ascenso.sytem.supplier.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
public class PurchaseWithItemsResponseDto {
    private UUID id;
    private String purchaseNumber;
    private String status;
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal tax;
    private BigDecimal total;
    private String notes;
    private LocalDateTime createdAt;
    private SupplierDto supplier;
    private List<PurchaseItemWithIdDto> items;

    @Builder
    @Getter
    @Setter
    public static class PurchaseItemWithIdDto {
        private UUID itemId;
        private String productName;
        private String productSku;
        private Integer orderedQuantity;
        private BigDecimal unitCost;
        private BigDecimal lineTotal;
    }

    @Builder
    @Getter
    @Setter
    public static class SupplierDto {
        private UUID id;
        private String name;
    }
}
