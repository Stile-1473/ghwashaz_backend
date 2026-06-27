package Ascenso.sytem.inventory.controller;

import Ascenso.sytem.common.response.ApiResponse;
import Ascenso.sytem.common.response.PageResponse;
import Ascenso.sytem.inventory.dto.InventoryResponseDto;
import Ascenso.sytem.inventory.dto.StockAdjustmentRequestDto;
import Ascenso.sytem.inventory.service.InventoryServiceContract;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryServiceContract inventoryService;

    @GetMapping("/{productId}")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).INVENTORY_VIEW)")
    public ApiResponse<InventoryResponseDto> getInventory(
            @PathVariable UUID productId){

        return ApiResponse.<InventoryResponseDto>builder()
                .success(true)
                .message("Inventory retrieved successfully.")
                .data(inventoryService.getInventory(productId))
                .build();

    }

    @GetMapping
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).INVENTORY_VIEW)")
    public ApiResponse<PageResponse<InventoryResponseDto>> getInventory(

            @RequestParam(required = false)
            String search,

            @RequestParam(required = false)
            Boolean lowStock,

            @PageableDefault(
                    page = 0,
                    size = 20,
                    sort = "product.name"
            )
            Pageable pageable){

        return ApiResponse.<PageResponse<InventoryResponseDto>>builder()
                .success(true)
                .message("Inventory retrieved successfully.")
                .data(
                        inventoryService.getInventory(
                                search,
                                lowStock,
                                pageable
                        )
                )
                .build();

    }

    @PatchMapping("/{productId}/adjust")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).INVENTORY_ADJUST)")
    public ApiResponse<Void> adjustStock(

            @PathVariable UUID productId,

            @Valid
            @RequestBody
            StockAdjustmentRequestDto dto){

        inventoryService.adjustStock(productId,dto);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Stock adjusted successfully.")
                .build();

    }

}