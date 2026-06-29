package Ascenso.sytem.finance.entity;

import Ascenso.sytem.cashier.entity.CashierSession;
import Ascenso.sytem.common.entity.BaseEntity;
import Ascenso.sytem.common.enums.CashSource;
import Ascenso.sytem.common.enums.CashTransactionType;
import Ascenso.sytem.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Cash Transaction - tracks all cash movements
 * 
 * Used for:
 * - Sale payments
 * - Owner withdrawals
 * - Cash float
 * - Expenses paid in cash
 * - Cash deposits
 */
@Entity
@Table(name = "cash_transactions",
        indexes = {
        @Index(name = "idx_cash_transaction_date", columnList = "createdAt"),
        @Index(name = "idx_cash_transaction_session", columnList = "session_id")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashTransaction extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String transactionNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private CashierSession cashierSession;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performed_by", nullable = false)
    private User performedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CashTransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private CashSource source;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;


    @Column(nullable = false)
    private Boolean cashIn;

    @Column(length = 500)
    private String reason;

    private String referenceNumber;
    
}
