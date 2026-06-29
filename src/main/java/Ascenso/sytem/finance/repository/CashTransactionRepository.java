package Ascenso.sytem.finance.repository;

import Ascenso.sytem.cashier.entity.CashierSession;
import Ascenso.sytem.finance.entity.CashTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CashTransactionRepository extends JpaRepository<CashTransaction, UUID>, JpaSpecificationExecutor<CashTransaction> {

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM CashTransaction t WHERE t.cashierSession = :session AND t.cashIn = :cashIn")
    Optional<BigDecimal> sumBySessionAndCashIn(
            @Param("session") CashierSession session,
            @Param("cashIn") Boolean cashIn);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM CashTransaction t WHERE " +
            "(:transactionType IS NULL OR t.transactionType = :transactionType) AND " +
            "(:cashIn IS NULL OR t.cashIn = :cashIn) AND " +
            "(:startDate IS NULL OR t.createdAt >= :startDate) AND " +
            "(:endDate IS NULL OR t.createdAt <= :endDate)")
    Optional<BigDecimal> sumByFilter(
            @Param("transactionType") String transactionType,
            @Param("cashIn") Boolean cashIn,
            @Param("startDate") java.time.LocalDateTime startDate,
            @Param("endDate") java.time.LocalDateTime endDate);

    @Query("SELECT COUNT(t) FROM CashTransaction t WHERE " +
            "(:transactionType IS NULL OR t.transactionType = :transactionType) AND " +
            "(:startDate IS NULL OR t.createdAt >= :startDate) AND " +
            "(:endDate IS NULL OR t.createdAt <= :endDate)")
    Long countByFilter(
            @Param("transactionType") String transactionType,
            @Param("startDate") java.time.LocalDateTime startDate,
            @Param("endDate") java.time.LocalDateTime endDate);
}
