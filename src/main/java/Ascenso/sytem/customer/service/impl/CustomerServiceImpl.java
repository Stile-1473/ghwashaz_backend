package Ascenso.sytem.customer.service.impl;

import Ascenso.sytem.audit.service.AuditServiceContract;
import Ascenso.sytem.common.enums.AuditActionType;
import Ascenso.sytem.common.enums.AuditModule;
import Ascenso.sytem.common.enums.CustomerType;
import Ascenso.sytem.common.mapper.PageMapper;
import Ascenso.sytem.common.response.PageResponse;
import Ascenso.sytem.customer.dto.CreateCustomerRequestDto;
import Ascenso.sytem.customer.dto.CustomerResponseDto;
import Ascenso.sytem.customer.dto.CustomerSaleResponseDto;
import Ascenso.sytem.customer.dto.UpdateCustomerRequestDto;
import Ascenso.sytem.customer.entity.Customer;
import Ascenso.sytem.customer.mapper.CustomerMapper;
import Ascenso.sytem.customer.repository.CustomerRepository;
import Ascenso.sytem.customer.service.CustomerServiceContract;
import Ascenso.sytem.customer.specification.CustomerSpecification;
import Ascenso.sytem.customer.validator.CustomerValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomerServiceImpl implements CustomerServiceContract {

    private final CustomerRepository customerRepository;
    private final CustomerValidator customerValidator;
    private final CustomerMapper customerMapper;
    private final AuditServiceContract auditServiceContract;

    @Override
    public CustomerResponseDto createCustomer(CreateCustomerRequestDto requestDto) {
        customerValidator.validatePhoneUnique(requestDto.getPhoneNumber());
        Customer customer = customerMapper.toEntity(requestDto);
        Customer saved = customerRepository.save(customer);
        try {
            auditServiceContract.log(AuditModule.CUSTOMER, AuditActionType.CREATE, saved.getId(),
                    "Created customer: " + saved.getFullName());
        } catch (Exception e) {
            log.warn("Audit log failed: {}", e.getMessage());
        }
        return customerMapper.toResponse(saved);
    }

    @Override
    public CustomerResponseDto getCustomer(UUID id) {
        return customerMapper.toResponse(customerValidator.getValidatedCustomer(id));
    }

    @Override
    public PageResponse<CustomerResponseDto> getCustomers(String search, CustomerType customerType, Boolean active, Pageable pageable) {
        Page<Customer> page = customerRepository.findAll(
                CustomerSpecification.search(search, customerType, active), pageable);
        return PageMapper.from(page.map(customerMapper::toResponse));
    }

    @Override
    public CustomerResponseDto updateCustomer(UUID customerId, UpdateCustomerRequestDto requestDto) {
        Customer customer = customerValidator.getValidatedCustomer(customerId);
        if (requestDto.getFullName() != null) customer.setFullName(requestDto.getFullName());
        if (requestDto.getPhoneNumber() != null) {
            customerValidator.validatePhoneUnique(requestDto.getPhoneNumber());
            customer.setPhoneNumber(requestDto.getPhoneNumber());
        }
        if (requestDto.getEmail() != null) customer.setEmail(requestDto.getEmail());
        if (requestDto.getAddress() != null) customer.setAddress(requestDto.getAddress());
        if (requestDto.getCustomerType() != null) customer.setCustomerType(requestDto.getCustomerType());
        Customer saved = customerRepository.save(customer);
        try {
            auditServiceContract.log(AuditModule.CUSTOMER, AuditActionType.UPDATE, saved.getId(),
                    "Updated customer: " + saved.getFullName());
        } catch (Exception e) {
            log.warn("Audit log failed: {}", e.getMessage());
        }
        return customerMapper.toResponse(saved);
    }

    @Override
    public PageResponse<CustomerSaleResponseDto> getCustomerSales(UUID customerId, Pageable pageable) {
        Customer customer = customerValidator.getValidatedCustomer(customerId);
        // TODO: Integrate with SaleRepository when available
        log.info("Getting sales for customer: {}", customer.getFullName());
        return PageMapper.from(Page.empty(pageable));
    }

    @Override
    public List<CustomerResponseDto> getTopCustomers(Integer limit) {
        int topLimit = (limit != null && limit > 0) ? limit : 10;
        Pageable pageable = PageRequest.of(0, topLimit);
        Page<Customer> page = customerRepository.findTopByActiveTrueOrderByCreatedAtDesc(pageable);
        return customerMapper.toResponseList(page.getContent());
    }
}
