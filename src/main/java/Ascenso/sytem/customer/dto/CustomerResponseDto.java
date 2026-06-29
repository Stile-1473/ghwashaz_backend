package Ascenso.sytem.customer.dto;

import Ascenso.sytem.common.enums.CustomerType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@Builder
public class CustomerResponseDto {
    private UUID id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
    private CustomerType customerType;
    private BigDecimal totalSpent;
    private Integer totalPurchases;
    private LocalDate lastPurchaseDate;
    private Boolean active;
    private LocalDateTime createdAt;
}
