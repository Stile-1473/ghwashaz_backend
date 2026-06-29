package Ascenso.sytem.cashier.repository;

import Ascenso.sytem.cashier.entity.CashierSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CashierSessionRepository extends JpaRepository<CashierSession, UUID> {
    Optional<CashierSession> findByActiveTrue();
    Optional<CashierSession> findByCashierIdAndActiveTrue(UUID cashierId);
}
