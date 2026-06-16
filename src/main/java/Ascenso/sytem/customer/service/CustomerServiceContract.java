package Ascenso.sytem.customer.service;

import Ascenso.sytem.common.enums.CustomerType;
import Ascenso.sytem.customer.dto.CreateCustomerRequestDto;
import Ascenso.sytem.customer.dto.CustomerResponseDto;
import Ascenso.sytem.customer.dto.CustomerSaleResponseDto;
import Ascenso.sytem.customer.dto.UpdateCustomerRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CustomerServiceContract {

    CustomerResponseDto createCustomer(CreateCustomerRequestDto requestDto);

    CustomerResponseDto getCustomer(UUID id);

    Page<CustomerResponseDto> getCustomers(
            String search,
            CustomerType customerType,
            Boolean active,
            Pageable pageable
    );

    CustomerResponseDto updateCustomer(UUID customerId, UpdateCustomerRequestDto requestDto);

    Page<CustomerSaleResponseDto> getCustomerSales(
            UUID customerId,
            Pageable pageable
    );


    List<CustomerResponseDto> getTopCustomers(Integer limit);
}
