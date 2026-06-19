package Ascenso.sytem.cash.entity;

import Ascenso.sytem.common.entity.BaseEntity;
import Ascenso.sytem.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name="cash_drawer"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CashDrawer extends BaseEntity {

    @Column(nullable = false)
    private BigDecimal currentBalance;

    @Column(nullable = false)
    private BigDecimal openingBalance;

    @Column(nullable = false)
    private LocalDate businessDate;

    @ManyToOne
    @JoinColumn(name = "cashier_id")
    private User cashier;

    @Column(nullable = false)
    private Boolean open;
}
