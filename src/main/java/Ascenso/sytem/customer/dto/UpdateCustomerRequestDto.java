package Ascenso.sytem.customer.dto;

import Ascenso.sytem.common.enums.CustomerType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCustomerRequestDto {

    private String fullName;

    private String phoneNumber;

    private String address;

    private CustomerType customerType;

    private String notes;
}
