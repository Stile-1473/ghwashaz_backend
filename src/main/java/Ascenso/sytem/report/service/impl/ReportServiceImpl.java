package Ascenso.sytem.report.service.impl;

import Ascenso.sytem.finance.entity.Expense;
import Ascenso.sytem.common.enums.ExpenseCategory;
import Ascenso.sytem.finance.repository.CashTransactionRepository;
import Ascenso.sytem.finance.repository.ExpenseRepository;
import Ascenso.sytem.inventory.entity.Inventory;
import Ascenso.sytem.inventory.repository.InventoryRepository;
import Ascenso.sytem.product.entity.Product;
import Ascenso.sytem.product.repository.ProductRepository;
import Ascenso.sytem.report.dto.*;
import Ascenso.sytem.report.service.DailySalesReportServiceContract;
import Ascenso.sytem.report.service.ReportServiceContract;
import Ascenso.sytem.sale.entity.Sale;
import Ascenso.sytem.sale.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportServiceContract {

    private final SaleRepository saleRepository;
    private final ExpenseRepository expenseRepository;
    private final CashTransactionRepository cashTransactionRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final DailySalesReportServiceContract dailySalesReportService;

    @Override
    public DailySalesReportResponseDto getDailySalesReport(LocalDate date) {
        return dailySalesReportService.getDailyReport(date);
    }

    @Override
    public SalesReportResponseDto getSalesByPeriod(ReportQueryDto query) {
        LocalDate startDate = query.getStartDate() != null ? query.getStartDate() : LocalDate.now().minusDays(30);
        LocalDate endDate = query.getEndDate() != null ? query.getEndDate() : LocalDate.now();

        var start = startDate.atStartOfDay();
        var end = endDate.atTime(LocalTime.MAX);

        List<Sale> sales = saleRepository.findAll().stream()
                .filter(s -> s.getCreatedAt() != null &&
                        !s.getCreatedAt().isBefore(start) &&
                        !s.getCreatedAt().isAfter(end))
                .collect(Collectors.toList());

        BigDecimal total = sales.stream()
                .map(Sale::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return SalesReportResponseDto.builder()
                .totalSales(total)
                .transactionCount((long) sales.size())
                .startDate(startDate)
                .endDate(endDate)
                .sales(sales.stream().limit(100).map(s -> 
                    SalesReportResponseDto.SaleDto.builder()
                        .saleNumber(s.getSaleNumber())
                        .totalAmount(s.getTotal())
                        .cashier(s.getCashier() != null ? s.getCashier().getFullName() : null)
                        .date(s.getCreatedAt())
                        .build()
                ).collect(Collectors.toList()))
                .build();
    }

    @Override
    public ExpenseReportResponseDto getExpenseReport(ReportQueryDto query) {
        LocalDate startDate = query.getStartDate() != null ? query.getStartDate() : LocalDate.now().minusDays(30);
        LocalDate endDate = query.getEndDate() != null ? query.getEndDate() : LocalDate.now();

        var start = startDate.atStartOfDay();
        var end = endDate.atTime(LocalTime.MAX);

        List<Expense> expenses = expenseRepository.findAll().stream()
                .filter(e -> e.getCreatedAt() != null &&
                        !e.getCreatedAt().isBefore(start) &&
                        !e.getCreatedAt().isAfter(end))
                .collect(Collectors.toList());

        BigDecimal total = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<ExpenseCategory, BigDecimal> byCategory = expenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.reducing(BigDecimal.ZERO, Expense::getAmount, BigDecimal::add)
                ));

        List<ExpenseReportResponseDto.CategoryExpenseDto> categoryList = byCategory.entrySet().stream()
                .map(e -> ExpenseReportResponseDto.CategoryExpenseDto.builder()
                        .category(e.getKey().name())
                        .total(e.getValue())
                        .count((long) expenses.stream()
                                .filter(exp -> exp.getCategory() == e.getKey()).count())
                        .build())
                .collect(Collectors.toList());

        return ExpenseReportResponseDto.builder()
                .totalExpenses(total)
                .transactionCount((long) expenses.size())
                .byCategory(categoryList)
                .build();
    }

    @Override
    public CashFlowReportResponseDto getCashFlowReport(ReportQueryDto query) {
        LocalDate startDate = query.getStartDate() != null ? query.getStartDate() : LocalDate.now().minusDays(30);
        LocalDate endDate = query.getEndDate() != null ? query.getEndDate() : LocalDate.now();

        var start = startDate.atStartOfDay();
        var end = endDate.atTime(LocalTime.MAX);

        var transactions = cashTransactionRepository.findAll().stream()
                .filter(t -> t.getCreatedAt() != null &&
                        !t.getCreatedAt().isBefore(start) &&
                        !t.getCreatedAt().isAfter(end))
                .collect(Collectors.toList());

        BigDecimal totalCashIn = transactions.stream()
                .filter(t -> t.getCashIn() != null && t.getCashIn())
                .map(t -> t.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCashOut = transactions.stream()
                .filter(t -> t.getCashIn() == null || !t.getCashIn())
                .map(t -> t.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<CashFlowReportResponseDto.TransactionDto> txList = transactions.stream().limit(100)
                .map(t -> CashFlowReportResponseDto.TransactionDto.builder()
                        .transactionNumber(t.getTransactionNumber())
                        .type(t.getTransactionType().name())
                        .amount(t.getAmount())
                        .reason(t.getReason())
                        .performedBy(t.getPerformedBy().getFullName())
                        .build())
                .collect(Collectors.toList());

        return CashFlowReportResponseDto.builder()
                .totalCashIn(totalCashIn)
                .totalCashOut(totalCashOut)
                .netChange(totalCashIn.subtract(totalCashOut))
                .transactions(txList)
                .build();
    }

    @Override
    public InventoryReportResponseDto getInventoryReport() {
        List<Product> products = productRepository.findAll();
        List<Inventory> inventories = inventoryRepository.findAll();

        long totalProducts = products.size();
        long totalQuantity = inventories.stream()
                .mapToLong(i -> i.getQuantityAvailable() != null ? i.getQuantityAvailable() : 0L)
                .sum();

        BigDecimal totalValue = BigDecimal.ZERO;
        BigDecimal totalCost = BigDecimal.ZERO;

        for (Inventory inv : inventories) {
            if (inv.getProduct() != null && inv.getQuantityAvailable() != null) {
                totalValue = totalValue.add(
                        inv.getProduct().getSellingPrice() != null 
                                ? inv.getProduct().getSellingPrice().multiply(BigDecimal.valueOf(inv.getQuantityAvailable()))
                                : BigDecimal.ZERO
                );
                totalCost = totalCost.add(
                        inv.getProduct().getCostPrice() != null
                                ? inv.getProduct().getCostPrice().multiply(BigDecimal.valueOf(inv.getQuantityAvailable()))
                                : BigDecimal.ZERO
                );
            }
        }

        return InventoryReportResponseDto.builder()
                .totalProducts(totalProducts)
                .totalQuantity(totalQuantity)
                .totalValue(totalValue)
                .totalCost(totalCost)
                .potentialProfit(totalValue.subtract(totalCost))
                .lowStock(null)
                .build();
    }

    @Override
    public ProfitLossReportResponseDto getProfitLossReport(ReportQueryDto query) {
        LocalDate startDate = query.getStartDate() != null ? query.getStartDate() : LocalDate.now().minusDays(30);
        LocalDate endDate = query.getEndDate() != null ? query.getEndDate() : LocalDate.now();

        var start = startDate.atStartOfDay();
        var end = endDate.atTime(LocalTime.MAX);

        List<Sale> sales = saleRepository.findAll().stream()
                .filter(s -> s.getCreatedAt() != null &&
                        !s.getCreatedAt().isBefore(start) &&
                        !s.getCreatedAt().isAfter(end))
                .collect(Collectors.toList());
        
        BigDecimal totalRevenue = sales.stream()
                .map(Sale::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Expense> expenses = expenseRepository.findAll().stream()
                .filter(e -> e.getCreatedAt() != null &&
                        !e.getCreatedAt().isBefore(start) &&
                        !e.getCreatedAt().isAfter(end))
                .collect(Collectors.toList());
        BigDecimal totalExpenses = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCost = BigDecimal.ZERO;
        BigDecimal grossProfit = totalRevenue.subtract(totalCost);
        BigDecimal netProfit = grossProfit.subtract(totalExpenses);

        return ProfitLossReportResponseDto.builder()
                .totalRevenue(totalRevenue)
                .totalCost(totalCost)
                .grossProfit(grossProfit)
                .totalExpenses(totalExpenses)
                .netProfit(netProfit)
                .transactionCount(sales.size())
                .build();
    }
}
