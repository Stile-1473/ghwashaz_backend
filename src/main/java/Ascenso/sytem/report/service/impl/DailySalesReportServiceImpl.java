package Ascenso.sytem.report.service.impl;

import Ascenso.sytem.finance.repository.ExpenseRepository;
import Ascenso.sytem.product.repository.ProductRepository;
import Ascenso.sytem.sale.repository.SaleRepository;
import Ascenso.sytem.report.dto.DailySalesReportResponseDto;
import Ascenso.sytem.report.service.DailySalesReportServiceContract;
import Ascenso.sytem.sale.entity.Sale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Daily Sales Report Service Implementation
 * 
 * Provides daily sales summary reports.
 */
@Service
@RequiredArgsConstructor
public class DailySalesReportServiceImpl implements DailySalesReportServiceContract {

    private final SaleRepository saleRepository;
    private final ExpenseRepository expenseRepository;
    private final ProductRepository productRepository;

    /**
     * Get daily sales report for a specific date
     * 
     * @param date - Target date (defaults to today)
     * @return Daily sales summary
     */
    @Override
    public DailySalesReportResponseDto getDailyReport(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        
        var startOfDay = date.atStartOfDay();
        var endOfDay = date.atTime(LocalTime.MAX);
        
        // Get sales for the day
        List<Sale> sales = saleRepository.findAll().stream()
                .filter(s -> s.getCreatedAt() != null && 
                        !s.getCreatedAt().isBefore(startOfDay) && 
                        !s.getCreatedAt().isAfter(endOfDay))
                .toList();
        
        // Calculate totals
        BigDecimal totalSales = sales.stream()
                .map(Sale::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalExpenses = expenseRepository.findAll().stream()
                .filter(e -> e.getCreatedAt() != null &&
                        !e.getCreatedAt().isBefore(startOfDay) &&
                        !e.getCreatedAt().isAfter(endOfDay))
                .map(e -> e.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Get top products (placeholder - would need SaleItem repository)
        List<DailySalesReportResponseDto.TopProductDto> topProducts = new ArrayList<>();
        
        // Get top customers (placeholder - would need customer analysis)
        List<DailySalesReportResponseDto.TopCustomerDto> topCustomers = new ArrayList<>();
        
        return DailySalesReportResponseDto.builder()
                .totalSales(totalSales)
                .totalExpenses(totalExpenses)
                .netCash(totalSales.subtract(totalExpenses))
                .totalTransactions(sales.size())
                .topProducts(topProducts)
                .topCustomers(topCustomers)
                .build();
    }
}
