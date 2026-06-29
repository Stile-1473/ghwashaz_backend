package Ascenso.sytem.sale.entity;

import Ascenso.sytem.cashier.entity.CashierSession;
import Ascenso.sytem.common.entity.BaseEntity;
import Ascenso.sytem.common.enums.PaymentMethod;
import Ascenso.sytem.common.enums.SaleStatus;
import Ascenso.sytem.customer.entity.Customer;
import Ascenso.sytem.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(
        name = "sales",
        indexes = {
                @Index(name = "idx_sale_date", columnList = "createdAt"),
                @Index(name = "idx_sale_cashier", columnList = "cashier_id"),
                @Index(name = "idx_sale_number", columnList = "saleNumber", unique = true)
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sale extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String saleNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cashier_id", nullable = false)
    private User cashier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private CashierSession cashierSession;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private SaleStatus status = SaleStatus.COMPLETED;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal subtotal;

    @Column(nullable = false, precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal discount = BigDecimal.ZERO;

    @Column(nullable = false, precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal tax = BigDecimal.ZERO;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal total;

    @Column(length = 500)
    private String notes;

    @OneToMany(
            mappedBy = "sale",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<SaleItem> items = new ArrayList<>();

    
    @OneToMany(
            mappedBy = "sale",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<SalePayment> payments = new ArrayList<>();

}
