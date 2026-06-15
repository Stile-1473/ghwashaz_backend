package Ascenso.sytem.purchase.entity;

import Ascenso.sytem.common.enums.PurchaseOrderStatus;
import Ascenso.sytem.supplier.entity.Supplier;
import Ascenso.sytem.user.entity.User;
import Ascenso.sytem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchase_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder extends BaseEntity {

    private String orderNumber;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User createdBy;


    @Enumerated(EnumType.STRING)
    private PurchaseOrderStatus status;


    @OneToMany(
            mappedBy = "purchaseOrder",
            cascade = CascadeType.ALL
    )
    private List<PurchaseOrderItem> items = new ArrayList<>();

}
