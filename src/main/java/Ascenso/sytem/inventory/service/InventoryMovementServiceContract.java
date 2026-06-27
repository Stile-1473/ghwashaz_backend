package Ascenso.sytem.inventory.service;

import Ascenso.sytem.common.response.PageResponse;
import Ascenso.sytem.inventory.dto.InventoryMovementResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface InventoryMovementServiceContract {

    PageResponse<InventoryMovementResponseDto> getMovements(
            UUID productId,
            Pageable pageable
    );
}

