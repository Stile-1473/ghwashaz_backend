package Ascenso.sytem.inventory.controller;

import Ascenso.sytem.common.constants.Permissions;
import Ascenso.sytem.common.response.ApiResponse;
import Ascenso.sytem.common.response.PageResponse;
import Ascenso.sytem.inventory.dto.InventoryMovementResponseDto;
import Ascenso.sytem.inventory.service.InventoryMovementServiceContract;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventory/movements")
public class InventoryMovementController {

    private final InventoryMovementServiceContract movementService;

    @GetMapping
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).INVENTORY_VIEW)")
    public ApiResponse<PageResponse<InventoryMovementResponseDto>> getMovements(

            @RequestParam(required = false)
            UUID productId,

            @PageableDefault(
                    page = 0,
                    size = 20,
                    sort = "createdAt"
            )
            Pageable pageable){

        return ApiResponse.<PageResponse<InventoryMovementResponseDto>>builder()
                .success(true)
                .message("Inventory movements retrieved successfully.")
                .data(
                        movementService.getMovements(
                                productId,
                                pageable
                        )
                )
                .build();

    }

}