package Ascenso.sytem.sale.entity;

import Ascenso.sytem.customer.entity.Customer;
import Ascenso.sytem.common.enums.PaymentMethod;
import Ascenso.sytem.common.enums.SaleStatus;
import Ascenso.sytem.user.entity.User;
import Ascenso.sytem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "sales",
        indexes = {
                @Index(name = "idx_sale_date",columnList = "createdAt")
               ,@Index(name ="idx_sale_cashier",columnList = "cashier_id")
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sale extends BaseEntity {

    private String saleNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cashier_id",nullable = false)
    private User cashier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(nullable = false,precision = 12,scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SaleStatus saleStatus = SaleStatus.COMPLETED;

    @OneToMany(
            mappedBy = "sale",
            cascade = CascadeType.ALL
    )
    private List<SaleItem> items = new ArrayList<>();















}
