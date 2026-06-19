package Ascenso.sytem.cash.entity;

import Ascenso.sytem.common.entity.BaseEntity;
import Ascenso.sytem.common.enums.CashTransactionType;
import Ascenso.sytem.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "cash_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CashTransaction extends BaseEntity {
 @ManyToOne
 @JoinColumn(name = "cash_drawer_id")
 private CashDrawer cashDrawer;

 @Enumerated(EnumType.STRING)
 private CashTransactionType type;

 @Column(nullable = false)
 private BigDecimal amount;

 private String reason;

 private UUID referenceId;

 @ManyToOne
 @JoinColumn(name = "performed_by")
 private User performedBy;
}
