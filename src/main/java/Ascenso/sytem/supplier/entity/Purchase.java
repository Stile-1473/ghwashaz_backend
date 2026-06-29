package Ascenso.sytem.supplier.entity;

import Ascenso.sytem.common.entity.BaseEntity;
import Ascenso.sytem.common.enums.PurchaseStatus;
import Ascenso.sytem.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "purchases")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Purchase extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String purchaseNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private User receivedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PurchaseStatus status;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal subtotal;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal discount;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal tax;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal total;

    @Column(length = 500)
    private String notes;

    @Column(length = 500)
    private String receiptImageUrl;

    @OneToMany(
            mappedBy = "purchase",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<PurchaseItem> items = new ArrayList<>();


}
