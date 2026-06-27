package Ascenso.sytem.inventory.entity;


import Ascenso.sytem.product.entity.Product;
import Ascenso.sytem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Table(
        name = "inventory",
        indexes = {
                @Index(
                        name = "idx_inventory_product",
                        columnList ="product_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "product_id",
            nullable = false,
            unique = true
    )
    private Product product;

    @Column(nullable = false)
    @Builder.Default
    private Integer quantityAvailable = 0;


    @Column(nullable = false)
    @Builder.Default
    private Integer quantityOnHand = 0;


    @Column(nullable = false)
    @Builder.Default
    private Integer quantityReserved = 0;


}
