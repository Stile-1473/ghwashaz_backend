package Ascenso.sytem.inventory.entity;


import Ascenso.sytem.common.enums.StockMovementType;
import Ascenso.sytem.product.entity.Product;
import Ascenso.sytem.user.entity.User;
import Ascenso.sytem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(
        name = "stock_movements",
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
@AllArgsConstructor
public class StockMovement extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StockMovementType type;


    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performed_by")
    private User performedBy;

    private UUID  referenceId;


}
