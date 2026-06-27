package Ascenso.sytem.inventory.entity;


import Ascenso.sytem.common.enums.InventoryMovementType;
import Ascenso.sytem.product.entity.Product;
import Ascenso.sytem.user.entity.User;
import Ascenso.sytem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(
        name = "inventory_movements",
        indexes = {
                @Index(
                        name = "idx_stock_product",
                        columnList = "product_id"
                ),
                @Index(
                        name = "idx_stock_date",
                        columnList = "createdAt"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class InventoryMovement extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InventoryMovementType type;


    @Column(nullable = false)
    private Integer balanceAfterMovement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performed_by")
    private User performedBy;


    @Column(length = 255)
    private String reference;

    @Column(length = 500)
    private String remarks;



}
