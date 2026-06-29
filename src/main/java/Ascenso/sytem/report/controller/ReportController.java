package Ascenso.sytem.report.controller;

import Ascenso.sytem.common.constants.Permissions;
import Ascenso.sytem.common.response.ApiResponse;
import Ascenso.sytem.report.dto.*;
import Ascenso.sytem.report.service.ReportServiceContract;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportServiceContract reportService;

    /**
     * Get daily sales report
     */
    @GetMapping("/daily-sales")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).REPORT_VIEW)")
    public ApiResponse<DailySalesReportResponseDto> getDailySalesReport(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date == null) date = LocalDate.now();
        return ApiResponse.<DailySalesReportResponseDto>builder()
                .success(true)
                .message("Daily sales report retrieved")
                .data(reportService.getDailySalesReport(date))
                .build();
    }

    /**
     * Get sales by period
     */
    @GetMapping("/sales")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).REPORT_VIEW)")
    public ApiResponse<SalesReportResponseDto> getSalesReport(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        ReportQueryDto query = ReportQueryDto.builder()
                .startDate(startDate)
                .endDate(endDate)
                .build();
        return ApiResponse.<SalesReportResponseDto>builder()
                .success(true)
                .message("Sales report retrieved")
                .data(reportService.getSalesByPeriod(query))
                .build();
    }

    /**
     * Get expense report
     */
    @GetMapping("/expenses")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).REPORT_VIEW)")
    public ApiResponse<ExpenseReportResponseDto> getExpenseReport(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        ReportQueryDto query = ReportQueryDto.builder()
                .startDate(startDate)
                .endDate(endDate)
                .build();
        return ApiResponse.<ExpenseReportResponseDto>builder()
                .success(true)
                .message("Expense report retrieved")
                .data(reportService.getExpenseReport(query))
                .build();
    }

    /**
     * Get cash flow report
     */
    @GetMapping("/cash-flow")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).REPORT_VIEW)")
    public ApiResponse<CashFlowReportResponseDto> getCashFlowReport(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        ReportQueryDto query = ReportQueryDto.builder()
                .startDate(startDate)
                .endDate(endDate)
                .build();
        return ApiResponse.<CashFlowReportResponseDto>builder()
                .success(true)
                .message("Cash flow report retrieved")
                .data(reportService.getCashFlowReport(query))
                .build();
    }

    /**
     * Get inventory report
     */
    @GetMapping("/inventory")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).REPORT_VIEW)")
    public ApiResponse<InventoryReportResponseDto> getInventoryReport() {
        return ApiResponse.<InventoryReportResponseDto>builder()
                .success(true)
                .message("Inventory report retrieved")
                .data(reportService.getInventoryReport())
                .build();
    }

    /**
     * Get profit & loss report
     */
    @GetMapping("/profit-loss")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).REPORT_VIEW)")
    public ApiResponse<ProfitLossReportResponseDto> getProfitLossReport(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        ReportQueryDto query = ReportQueryDto.builder()
                .startDate(startDate)
                .endDate(endDate)
                .build();
        return ApiResponse.<ProfitLossReportResponseDto>builder()
                .success(true)
                .message("Profit & loss report retrieved")
                .data(reportService.getProfitLossReport(query))
                .build();
    }
}
