package Ascenso.sytem.report.service;

import Ascenso.sytem.report.dto.DailySalesReportResponseDto;

import java.time.LocalDate;

/**
 * Daily Sales Report Service Contract
 * 
 * Provides daily sales summary reports for owner visibility.
 */
public interface DailySalesReportServiceContract {

    /**
     * Get daily sales report for a specific date
     * 
     * @param date - Target date (defaults to today)
     * @return Daily sales summary
     */
    DailySalesReportResponseDto getDailyReport(LocalDate date);
}
