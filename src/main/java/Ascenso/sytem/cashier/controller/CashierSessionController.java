package Ascenso.sytem.cashier.controller;

import Ascenso.sytem.cashier.dto.CashierSessionResponseDto;
import Ascenso.sytem.cashier.service.CashierSessionServiceContract;
import Ascenso.sytem.common.constants.Permissions;
import Ascenso.sytem.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/cashier/sessions")
@RequiredArgsConstructor
public class CashierSessionController {

    private final CashierSessionServiceContract cashierSessionService;

    @PostMapping
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).SALE_CREATE)")
    public ApiResponse<CashierSessionResponseDto> openSession(
            @RequestParam(required = false) BigDecimal openingBalance) {
        return ApiResponse.<CashierSessionResponseDto>builder()
                .success(true)
                .message("Session opened successfully")
                .data(cashierSessionService.openSession(openingBalance))
                .build();
    }

    @PatchMapping("/{id}/close")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).SALE_CREATE)")
    public ApiResponse<CashierSessionResponseDto> closeSession(
            @PathVariable UUID id,
            @RequestParam(required = false) BigDecimal closingBalance) {
        return ApiResponse.<CashierSessionResponseDto>builder()
                .success(true)
                .message("Session closed successfully")
                .data(cashierSessionService.closeSession(id, closingBalance))
                .build();
    }

    @GetMapping("/current")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).SALE_VIEW)")
    public ApiResponse<CashierSessionResponseDto> getCurrentSession() {
        return ApiResponse.<CashierSessionResponseDto>builder()
                .success(true)
                .message("Current session retrieved")
                .data(cashierSessionService.getCurrentSession())
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).SALE_VIEW)")
    public ApiResponse<CashierSessionResponseDto> getSession(@PathVariable UUID id) {
        return ApiResponse.<CashierSessionResponseDto>builder()
                .success(true)
                .message("Session retrieved")
                .data(cashierSessionService.getSession(id))
                .build();
    }
}
