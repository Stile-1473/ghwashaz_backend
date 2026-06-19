package Ascenso.sytem.cash.entity;


import Ascenso.sytem.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "cash_count")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CashCount extends BaseEntity {

    @ManyToOne
    private CashDrawer cashDrawer;

    @Column(nullable = false)
    private BigDecimal expectedAmount;

    @Column(nullable = false)
    private BigDecimal actualCountedAmount;

    private BigDecimal difference;

    private String comment;
}
