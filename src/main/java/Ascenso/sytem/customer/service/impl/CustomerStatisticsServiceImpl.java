package Ascenso.sytem.customer.service.impl;

import Ascenso.sytem.common.exception.ResourceNotFoundException;
import Ascenso.sytem.customer.dto.CustomerStatisticsDto;
import Ascenso.sytem.customer.entity.Customer;
import Ascenso.sytem.customer.repository.CustomerRepository;
import Ascenso.sytem.customer.service.CustomerStatisticsServiceContract;
import Ascenso.sytem.sale.entity.Sale;
import Ascenso.sytem.sale.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CustomerStatisticsServiceImpl implements CustomerStatisticsServiceContract {

    private final CustomerRepository customerRepository;
    private final SaleRepository saleRepository;

    @Override
    public CustomerStatisticsDto getStatistics(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        
        List<Sale> sales = saleRepository.findAll().stream()
                .filter(s -> s.getCustomer() != null)
                .filter(s -> s.getCustomer().getId().equals(customerId))
                .filter(s -> s.getStatus() == Ascenso.sytem.common.enums.SaleStatus.COMPLETED)
                .collect(Collectors.toList());
        
        int totalPurchases = sales.size();
        BigDecimal totalSpent = sales.stream()
                .map(Sale::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal averageSale = totalPurchases > 0 ? 
                totalSpent.divide(BigDecimal.valueOf(totalPurchases), 2, RoundingMode.HALF_UP) : 
                BigDecimal.ZERO;
        BigDecimal largestSale = sales.stream()
                .map(Sale::getTotal)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        LocalDateTime lastPurchase = sales.stream()
                .map(Sale::getCreatedAt)
                .max(LocalDateTime::compareTo)
                .orElse(null);
        
        log.info("Customer statistics for {}: {} purchases, {}", customer.getFullName(), totalPurchases, totalSpent);
        
        return CustomerStatisticsDto.builder()
                .totalPurchases(totalPurchases)
                .totalSpent(totalSpent)
                .averageSale(averageSale)
                .largestSale(largestSale)
                .lastPurchaseDate(lastPurchase != null ? lastPurchase.toString() : null)
                .returnsCount(0)
                .build();
    }

}
