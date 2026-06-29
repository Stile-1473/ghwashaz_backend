package Ascenso.sytem.cashier.entity;

import Ascenso.sytem.common.entity.BaseEntity;
import Ascenso.sytem.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cashier_sessions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashierSession extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cashier_id", nullable = false)
    private User cashier;

    @Column(nullable = false)
    private LocalDateTime loginTime;

    private LocalDateTime logoutTime;

    @Column(nullable = false, precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal openingBalance = BigDecimal.ZERO;

    @Column(nullable = false, precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal closingBalance = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;
}
