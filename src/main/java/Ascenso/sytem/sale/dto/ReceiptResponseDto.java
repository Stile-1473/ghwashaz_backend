package Ascenso.sytem.sale.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class ReceiptResponseDto {

    private UUID id;
    private String saleNumber;
    private String cashierName;
    private String customerName;
    private BigDecimal total;
    private List<ReceiptItem> items;
    private List<ReceiptPayment> payments;
    private LocalDateTime createdAt;
    
    @Getter
    @Builder
    public static class ReceiptItem {
        private String productName;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal lineTotal;
    }
    
    @Getter
    @Builder
    public static class ReceiptPayment {
        private String paymentMethod;
        private BigDecimal amount;
    }

}
