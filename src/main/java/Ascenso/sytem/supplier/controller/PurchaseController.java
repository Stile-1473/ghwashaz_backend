package Ascenso.sytem.supplier.controller;

import Ascenso.sytem.common.response.ApiResponse;
import Ascenso.sytem.common.response.PageResponse;
import Ascenso.sytem.supplier.dto.CreatePurchaseRequestDto;
import Ascenso.sytem.supplier.dto.PurchaseResponseDto;
import Ascenso.sytem.supplier.dto.PurchaseWithItemsResponseDto;
import Ascenso.sytem.supplier.dto.ReceivePurchaseRequestDto;
import Ascenso.sytem.supplier.service.PurchaseServiceContract;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/purchases")
public class PurchaseController {

    private final PurchaseServiceContract purchaseService;

    @PostMapping("/create-purchase")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).PURCHASE_CREATE)")
    public ApiResponse<PurchaseResponseDto> createPurchase(@Valid @RequestBody CreatePurchaseRequestDto dto) {
        return ApiResponse.<PurchaseResponseDto>builder()
                .success(true)
                .message("Purchase created successfully")
                .data(purchaseService.createPurchase(dto))
                .build();
    }

    @GetMapping("/{purchaseId}")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).PURCHASE_VIEW)")
    public ApiResponse<PurchaseResponseDto> getPurchase(@PathVariable UUID purchaseId) {
        return ApiResponse.<PurchaseResponseDto>builder()
                .success(true)
                .message("Purchase fetched successfully")
                .data(purchaseService.getPurchase(purchaseId))
                .build();
    }

    // Get purchase with item IDs for receiving
    @GetMapping("/{purchaseId}/with-items")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).PURCHASE_VIEW)")
    public ApiResponse<PurchaseWithItemsResponseDto> getPurchaseWithItems(@PathVariable UUID purchaseId) {
        return ApiResponse.<PurchaseWithItemsResponseDto>builder()
                .success(true)
                .message("Purchase with items fetched successfully")
                .data(purchaseService.getPurchaseWithItems(purchaseId))
                .build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).PURCHASE_VIEW)")
    public ApiResponse<PageResponse<PurchaseResponseDto>> searchPurchases(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) UUID supplierId,
            @RequestParam(required = false) String status,
            @PageableDefault(page = 0, size = 20, sort = "purchaseNumber") Pageable pageable) {
        return ApiResponse.<PageResponse<PurchaseResponseDto>>builder()
                .success(true)
                .message("Purchases fetched successfully")
                .data(purchaseService.getPurchases(search, supplierId, status, pageable))
                .build();
    }

    // Cashier receives purchase - can be partial or full
    @PatchMapping("/{purchaseId}/receive")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).PURCHASE_RECEIVE)")
    public ApiResponse<PurchaseResponseDto> receivePurchase(
            @PathVariable UUID purchaseId,
            @Valid @RequestBody ReceivePurchaseRequestDto dto) {
        return ApiResponse.<PurchaseResponseDto>builder()
                .success(true)
                .message("Delivery recorded")
                .data(purchaseService.receivePurchase(purchaseId, dto))
                .build();
    }

    // Owner approves short delivery
    @PatchMapping("/{purchaseId}/approve-short")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).PURCHASE_APPROVE_SHORT)")
    public ApiResponse<PurchaseResponseDto> approveShortDelivery(@PathVariable UUID purchaseId) {
        return ApiResponse.<PurchaseResponseDto>builder()
                .success(true)
                .message("Short delivery approved")
                .data(purchaseService.approveShortDelivery(purchaseId))
                .build();
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).PURCHASE_CANCEL)")
    public ApiResponse<Void> cancelPurchase(@PathVariable UUID id) {
        purchaseService.cancelPurchase(id);
        return ApiResponse.<Void>builder()
                .success(true)
                .message("Purchase Canceled")
                .data(null)
                .build();
    }
}
