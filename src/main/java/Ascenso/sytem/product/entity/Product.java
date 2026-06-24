package Ascenso.sytem.product.entity;

import Ascenso.sytem.common.entity.BaseEntity;
import Ascenso.sytem.common.enums.Unit;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "products",
        indexes = {
                @Index(name = "idx_product_name",columnList="name"),
                @Index(name = "idx_product_sku", columnList = "sku"),
                @Index(name = "idx_product_category",columnList="category_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {
    @Column(nullable = false, length = 150)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(unique = true,nullable = false)
    private String sku;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal sellingPrice;


    @Column(precision = 12, scale=2)
    private BigDecimal costPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Unit productUnit;

    @Column(length = 50, unique = true)
    private String barcode;

    @Column(nullable = false)
    private Integer minimumStockLevel = 0;

    @Column(nullable = false)
    private Boolean active = true;


    @Column(length = 500)
    private String imageUrl;

}
