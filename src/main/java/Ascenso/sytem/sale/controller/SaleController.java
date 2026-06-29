package Ascenso.sytem.sale.controller;

import Ascenso.sytem.common.enums.PaymentMethod;
import Ascenso.sytem.common.enums.SaleStatus;
import Ascenso.sytem.common.response.ApiResponse;
import Ascenso.sytem.common.response.PageResponse;
import Ascenso.sytem.sale.dto.CreateSaleRequestDto;
import Ascenso.sytem.sale.dto.ReceiptResponseDto;
import Ascenso.sytem.sale.dto.SaleResponseDto;
import Ascenso.sytem.sale.service.SaleServiceContract;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sales")
public class SaleController {

    private final SaleServiceContract saleService;

    @PostMapping
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).SALE_CREATE)")
    public ApiResponse<SaleResponseDto> createSale(
            @Valid @RequestBody CreateSaleRequestDto dto) {
        return ApiResponse.<SaleResponseDto>builder()
                .success(true)
                .message("Sale created successfully")
                .data(saleService.createSale(dto))
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).SALE_VIEW)")
    public ApiResponse<SaleResponseDto> getSale(@PathVariable UUID id) {
        return ApiResponse.<SaleResponseDto>builder()
                .success(true)
                .message("Sale retrieved successfully")
                .data(saleService.getSale(id))
                .build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).SALE_VIEW)")
    public ApiResponse<PageResponse<SaleResponseDto>> getSales(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) UUID cashierId,
            @RequestParam(required = false) UUID customerId,
            @RequestParam(required = false) PaymentMethod paymentMethod,
            @RequestParam(required = false) SaleStatus saleStatus,
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        
        Page<SaleResponseDto> page = saleService.getSales(
                startDate, endDate, cashierId, customerId, 
                paymentMethod, saleStatus, pageable);
        
        return ApiResponse.<PageResponse<SaleResponseDto>>builder()
                .success(true)
                .message("Sales retrieved successfully")
                .data(PageResponse.from(page))
                .build();
    }

    @GetMapping("/{id}/receipt")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).SALE_VIEW)")
    public ApiResponse<ReceiptResponseDto> getReceipt(@PathVariable UUID id) {
        return ApiResponse.<ReceiptResponseDto>builder()
                .success(true)
                .message("Receipt retrieved successfully")
                .data(saleService.getReceipt(id))
                .build();
    }

    @PatchMapping("/{id}/void")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).SALE_VOID)")
    public ApiResponse<Void> voidSale(@PathVariable UUID id) {
        saleService.voidSale(id);
        return ApiResponse.<Void>builder()
                .success(true)
                .message("Sale voided successfully")
                .build();
    }

}
