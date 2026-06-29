package Ascenso.sytem.finance.dto;

import Ascenso.sytem.common.enums.ExpenseCategory;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateExpenseRequestDto {

    @NotBlank(message = "Description is required")
    @Size(min = 3, max = 200, message = "Description must be between 3 and 200 characters")
    private String description;

    @NotNull(message = "Category is required")
    private ExpenseCategory category;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @DecimalMax(value = "1000000", message = "Amount exceeds maximum allowed")
    private BigDecimal amount;

    @Size(max = 100, message = "Supplier name too long")
    private String supplierName;

    @Size(max = 50, message = "Receipt number too long")
    private String receiptNumber;

    private Boolean paidFromCash;
}
