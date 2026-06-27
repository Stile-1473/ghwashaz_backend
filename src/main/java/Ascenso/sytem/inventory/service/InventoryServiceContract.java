package Ascenso.sytem.inventory.service;

import Ascenso.sytem.common.response.PageResponse;
import Ascenso.sytem.inventory.dto.InventoryResponseDto;
import Ascenso.sytem.inventory.dto.StockAdjustmentRequestDto;
import Ascenso.sytem.product.entity.Product;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface InventoryServiceContract {

    void createInventory(Product product);

    void receiveStock(Product product, Integer quantity);


    void reduceStock(
            Product product,
            Integer quantity,
            String reference,
            String remarks
    );

    void adjustStock(UUID productId, StockAdjustmentRequestDto requestDto);

    InventoryResponseDto getInventory(UUID productId);

    PageResponse<InventoryResponseDto> getInventory(
            String search,
            Boolean lowStock,
            Pageable pageable
    );

}

