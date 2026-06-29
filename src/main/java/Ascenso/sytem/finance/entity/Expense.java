package Ascenso.sytem.finance.entity;

import Ascenso.sytem.cashier.entity.CashierSession;
import Ascenso.sytem.common.entity.BaseEntity;
import Ascenso.sytem.common.enums.ExpenseCategory;
import Ascenso.sytem.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Expense - tracks business expenses
 * 
 * Categories include:
 * - Transport, Salaries, Electricity, Water, Internet
 * - Rent, Stationery, Cleaning, Fuel, Marketing
 */
@Entity
@Table(name = "expenses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Expense extends BaseEntity {

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private ExpenseCategory category;


    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recorded_by")
    private User recordedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private CashierSession cashierSession;


    private String receiptNumber;
    private String supplierName;
    
}
