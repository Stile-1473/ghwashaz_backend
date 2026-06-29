package Ascenso.sytem.report.service;

import Ascenso.sytem.report.dto.*;

import java.time.LocalDate;

public interface ReportServiceContract {

    DailySalesReportResponseDto getDailySalesReport(LocalDate date);

    SalesReportResponseDto getSalesByPeriod(ReportQueryDto query);

    ExpenseReportResponseDto getExpenseReport(ReportQueryDto query);

    CashFlowReportResponseDto getCashFlowReport(ReportQueryDto query);

    InventoryReportResponseDto getInventoryReport();

    ProfitLossReportResponseDto getProfitLossReport(ReportQueryDto query);
}
