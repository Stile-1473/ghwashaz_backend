package Ascenso.sytem.finance.controller;

import Ascenso.sytem.common.constants.Permissions;
import Ascenso.sytem.common.response.ApiResponse;
import Ascenso.sytem.common.response.PageResponse;
import Ascenso.sytem.finance.dto.CreateExpenseRequestDto;
import Ascenso.sytem.finance.dto.ExpenseResponseDto;
import Ascenso.sytem.finance.service.FinanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/finance")
public class FinanceController {

    private final FinanceService financeService;

    @PostMapping("/expenses")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).EXPENSE_CREATE)")
    public ApiResponse<ExpenseResponseDto> recordExpense(
            @Valid @RequestBody CreateExpenseRequestDto dto) {
        return ApiResponse.<ExpenseResponseDto>builder()
                .success(true)
                .message("Expense recorded successfully")
                .data(financeService.recordExpense(dto))
                .build();
    }

    @GetMapping("/expenses/{id}")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).EXPENSE_VIEW)")
    public ApiResponse<ExpenseResponseDto> getExpense(@PathVariable UUID id) {
        return ApiResponse.<ExpenseResponseDto>builder()
                .success(true)
                .message("Expense retrieved successfully")
                .data(financeService.getExpense(id))
                .build();
    }

    @GetMapping("/expenses")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).EXPENSE_VIEW)")
    public ApiResponse<PageResponse<ExpenseResponseDto>> getExpenses(
            @PageableDefault(size = 20) Pageable pageable) {
        return ApiResponse.<PageResponse<ExpenseResponseDto>>builder()
                .success(true)
                .message("Expenses retrieved successfully")
                .data(financeService.getExpenses(pageable))
                .build();
    }

}
