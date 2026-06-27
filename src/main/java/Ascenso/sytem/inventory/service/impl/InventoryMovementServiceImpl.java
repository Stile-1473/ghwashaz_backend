package Ascenso.sytem.inventory.service.impl;

import Ascenso.sytem.common.mapper.PageMapper;
import Ascenso.sytem.common.response.PageResponse;
import Ascenso.sytem.inventory.dto.InventoryMovementResponseDto;
import Ascenso.sytem.inventory.mapper.InventoryMapper;
import Ascenso.sytem.inventory.repository.InventoryMovementRepository;
import Ascenso.sytem.inventory.service.InventoryMovementServiceContract;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryMovementServiceImpl implements InventoryMovementServiceContract {

    private final InventoryMovementRepository movementRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    public PageResponse<InventoryMovementResponseDto> getMovements(
            UUID productId,
            Pageable pageable
    ) {
        // If productId filtering is required, we should add proper repository/specification support.
        // For now, show movements paginated; productId is ignored until repository filtering is added.
        return PageMapper.from(
                movementRepository.findAll(pageable).map(inventoryMapper::toMovementResponse)
        );

    }
}

