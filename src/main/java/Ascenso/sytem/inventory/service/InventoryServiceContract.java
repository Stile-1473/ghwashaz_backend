package Ascenso.sytem.inventory.service;

import Ascenso.sytem.common.enums.StockMovementType;
import Ascenso.sytem.inventory.dto.InventoryResponseDto;
import Ascenso.sytem.inventory.dto.StockAdjustmentRequestDto;
import Ascenso.sytem.inventory.dto.StockMovementResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface InventoryServiceContract {

    Page<InventoryResponseDto> getInventory(
            String search,
            UUID categoryId,
            Boolean lowStock,
            Pageable pageable
    );

    InventoryResponseDto getProductInventory(UUID productId);

    InventoryResponseDto adjustStock(StockAdjustmentRequestDto requestDto);

    Page<StockMovementResponseDto> getMovements(
            UUID productId,
            StockMovementType type,
            LocalDate startDate,
            LocalDate endDate,
            UUID userId,
            Pageable pageable
    );

    List<InventoryResponseDto> getLowStockProducts();

}
