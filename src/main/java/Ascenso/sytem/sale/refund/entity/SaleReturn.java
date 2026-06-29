package Ascenso.sytem.sale.refund.entity;

import Ascenso.sytem.common.entity.BaseEntity;
import Ascenso.sytem.sale.entity.Sale;
import Ascenso.sytem.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "sale.refunds")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleReturn extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original_sale_id", nullable = false)
    private Sale originalSale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processed_by", nullable = false)
    private User processedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReturnType returnType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RefundMethod refundMethod;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal refundAmount;

    @Column(length = 500)
    private String reason;

    private String referenceNumber;

}
