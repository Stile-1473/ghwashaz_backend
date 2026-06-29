package Ascenso.sytem.customer.validator;

import Ascenso.sytem.common.exception.BadRequestException;
import Ascenso.sytem.common.exception.ResourceNotFoundException;
import Ascenso.sytem.customer.entity.Customer;
import Ascenso.sytem.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
@RequiredArgsConstructor
public class CustomerValidator {

    private final CustomerRepository customerRepository;
    

    public Customer getValidatedCustomer(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }
    

    public void validatePhoneUnique(String phone) {
        if (phone != null && !phone.isBlank()) {
            customerRepository.findByPhoneNumber(phone)
                    .ifPresent(c -> {
                        throw new BadRequestException("Phone number already registered");
                    });
        }
    }
    
}
