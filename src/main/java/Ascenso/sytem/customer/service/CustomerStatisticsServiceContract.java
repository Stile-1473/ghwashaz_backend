package Ascenso.sytem.customer.service;

import Ascenso.sytem.customer.dto.CustomerStatisticsDto;


public interface CustomerStatisticsServiceContract {
    CustomerStatisticsDto getStatistics(java.util.UUID customerId);
}
