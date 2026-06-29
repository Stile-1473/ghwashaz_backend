package Ascenso.sytem.customer.controller;

import Ascenso.sytem.common.enums.CustomerType;
import Ascenso.sytem.common.response.ApiResponse;
import Ascenso.sytem.common.response.PageResponse;
import Ascenso.sytem.customer.dto.CreateCustomerRequestDto;
import Ascenso.sytem.customer.dto.CustomerResponseDto;
import Ascenso.sytem.customer.dto.CustomerSaleResponseDto;
import Ascenso.sytem.customer.dto.UpdateCustomerRequestDto;
import Ascenso.sytem.customer.service.CustomerServiceContract;
import Ascenso.sytem.customer.service.CustomerStatisticsServiceContract;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerServiceContract customerService;
    private final CustomerStatisticsServiceContract statisticsService;

    @PostMapping
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).CUSTOMER_CREATE)")
    public ApiResponse<CustomerResponseDto> createCustomer(@Valid @RequestBody CreateCustomerRequestDto request) {
        return ApiResponse.<CustomerResponseDto>builder()
                .success(true)
                .message("Customer created successfully")
                .data(customerService.createCustomer(request))
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).CUSTOMER_VIEW)")
    public ApiResponse<CustomerResponseDto> getCustomer(@PathVariable UUID id) {
        return ApiResponse.<CustomerResponseDto>builder()
                .success(true)
                .message("Customer fetched successfully")
                .data(customerService.getCustomer(id))
                .build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).CUSTOMER_VIEW)")
    public ApiResponse<PageResponse<CustomerResponseDto>> searchCustomers(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) CustomerType customerType,
            @RequestParam(required = false) Boolean active,
            @PageableDefault(page = 0, size = 20, sort = "fullName") Pageable pageable) {
        return ApiResponse.<PageResponse<CustomerResponseDto>>builder()
                .success(true)
                .message("Customers fetched successfully")
                .data(customerService.getCustomers(search, customerType, active, pageable))
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).CUSTOMER_CREATE)")
    public ApiResponse<CustomerResponseDto> updateCustomer(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCustomerRequestDto request) {
        return ApiResponse.<CustomerResponseDto>builder()
                .success(true)
                .message("Customer updated successfully")
                .data(customerService.updateCustomer(id, request))
                .build();
    }

    @GetMapping("/{id}/sales")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).CUSTOMER_VIEW)")
    public ApiResponse<PageResponse<CustomerSaleResponseDto>> getCustomerSales(
            @PathVariable UUID id,
            @PageableDefault(page = 0, size = 20) Pageable pageable) {
        return ApiResponse.<PageResponse<CustomerSaleResponseDto>>builder()
                .success(true)
                .message("Customer sales fetched successfully")
                .data(customerService.getCustomerSales(id, pageable))
                .build();
    }

    @GetMapping("/{id}/statistics")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).CUSTOMER_VIEW)")
    public ApiResponse<?> getStatistics(@PathVariable UUID id) {
        return ApiResponse.builder()
                .success(true)
                .data(statisticsService.getStatistics(id))
                .build();
    }

    @GetMapping("/top")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).CUSTOMER_VIEW)")
    public ApiResponse<?> getTopCustomers(@RequestParam(defaultValue = "10") Integer limit) {
        return ApiResponse.builder()
                .success(true)
                .data(customerService.getTopCustomers(limit))
                .build();
    }
}
