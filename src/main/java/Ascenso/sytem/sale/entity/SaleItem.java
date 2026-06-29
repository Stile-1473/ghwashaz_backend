package Ascenso.sytem.sale.entity;

import Ascenso.sytem.common.entity.BaseEntity;
import Ascenso.sytem.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * SaleItem - individual item in a sale
 *
 * Enterprise Feature: Pricing Snapshot
 * Stores both cost and selling price at time of sale
 * This preserves accurate profit reporting even if prices change later
 */
@Entity
@Table(name = "sale_items")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal costPrice;

    @Column(nullable = false, precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal discount = BigDecimal.ZERO;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal lineTotal;

}
