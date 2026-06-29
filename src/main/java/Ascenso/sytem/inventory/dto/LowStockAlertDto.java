package Ascenso.sytem.inventory.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class LowStockAlertDto {
    private UUID productId;
    private String productName;
    private String sku;
    private int currentStock;
    private int minimumStock;
    private int shortage;
}
