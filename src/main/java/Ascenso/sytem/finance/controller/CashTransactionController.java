package Ascenso.sytem.finance.controller;

import Ascenso.sytem.common.constants.Permissions;
import Ascenso.sytem.common.response.ApiResponse;
import Ascenso.sytem.common.response.PageResponse;
import Ascenso.sytem.finance.dto.*;
import Ascenso.sytem.finance.service.CashTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/finance/cash")
public class CashTransactionController {

    private final CashTransactionService cashTransactionService;

    @PostMapping("/transactions")
    public ApiResponse<CashTransactionResponseDto> createTransaction(
            @Valid @RequestBody CashTransactionDto dto) {
        String permission = dto.getCashIn()
                ? Permissions.CASH_DEPOSIT
                : Permissions.CASH_WITHDRAW;
        

        return ApiResponse.<CashTransactionResponseDto>builder()
                .success(true)
                .message("Cash transaction created successfully")
                .data(cashTransactionService.createTransaction(dto))
                .build();
    }

    @GetMapping("/transactions/{id}")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).CASH_VIEW)")
    public ApiResponse<CashTransactionResponseDto> getTransaction(@PathVariable UUID id) {
        return ApiResponse.<CashTransactionResponseDto>builder()
                .success(true)
                .message("Transaction retrieved successfully")
                .data(cashTransactionService.getTransaction(id))
                .build();
    }

    @GetMapping("/transactions")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).CASH_VIEW)")
    public ApiResponse<PageResponse<CashTransactionResponseDto>> getTransactions(
            @RequestParam(required = false) String transactionType,
            @RequestParam(required = false) Boolean cashIn,
            @RequestParam(required = false) String source,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @PageableDefault(size = 20) Pageable pageable) {
        
        CashTransactionQueryDto query = CashTransactionQueryDto.builder()
                .cashIn(cashIn)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        
        return ApiResponse.<PageResponse<CashTransactionResponseDto>>builder()
                .success(true)
                .message("Transactions retrieved successfully")
                .data(cashTransactionService.getTransactions(query, pageable))
                .build();
    }

    @GetMapping("/balance")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).CASH_VIEW)")
    public ApiResponse<CashBalanceDto> getBalance() {
        return ApiResponse.<CashBalanceDto>builder()
                .success(true)
                .message("Balance retrieved successfully")
                .data(cashTransactionService.getCurrentBalance())
                .build();
    }

    @GetMapping("/summary")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).CASH_VIEW)")
    public ApiResponse<CashSummaryDto> getSummary(
            @RequestParam(required = false) Boolean cashIn,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        
        CashTransactionQueryDto query = CashTransactionQueryDto.builder()
                .cashIn(cashIn)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        
        return ApiResponse.<CashSummaryDto>builder()
                .success(true)
                .message("Summary retrieved successfully")
                .data(cashTransactionService.getSummary(query))
                .build();
    }
}
