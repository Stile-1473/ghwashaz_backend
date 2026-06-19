package Ascenso.sytem.report.service;

import Ascenso.sytem.common.enums.PaymentMethod;
import Ascenso.sytem.report.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ReportServiceContract {

    DashboardResponseDto getDashBoard();

    Page<SalesReportResponseDto> getSalesReport(
            LocalDate startDate,
            LocalDate endDate,
            UUID cashierId,
            UUID customerId,
            PaymentMethod paymentMethod,
            Pageable pageable
    );

    ProfitReportResponseDto getProfitReport(
            LocalDate startDate,
            LocalDate endDate
    );

    List<TopProductReportResponseDto> getTopProducts(
            Integer limit,
            LocalDate startDate,
            LocalDate endDate
    );

    List<CustomerAnalyticsResponseDto> getCustomerAnalytics(Integer limit);

    List<CashierReportResponseDto> getCashierPerformance(
            LocalDate startDate,
            LocalDate endDate
    );

    List<InventoryValueResponseDto> getInventoryValue();

}
