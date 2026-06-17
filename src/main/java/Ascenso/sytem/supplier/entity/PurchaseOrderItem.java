package Ascenso.sytem.supplier.entity;


import Ascenso.sytem.product.entity.Product;
import Ascenso.sytem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "purchase_order_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private PurchaseOrder purchaseOrder;


    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Column(nullable = false)
    private Integer orderedQuantity;

    @Column(nullable = false)
    private Integer receivedQuantity = 0;
}
