package Ascenso.sytem.sale.entity;

import Ascenso.sytem.common.entity.BaseEntity;
import Ascenso.sytem.common.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * SalePayment - payment record for a sale
 * 
 * Enterprise Feature: Multiple Payments
 * Supports split payments (Cash + EcoCash + Innbucks)
 * Also supports partial payments for credit sales
 */
@Entity
@Table(name = "sale_payments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalePayment extends BaseEntity {

    // The sale this payment belongs to
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    // Payment method used
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    // Amount paid via this method
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    // Transaction reference
    private String transactionReference;

}
