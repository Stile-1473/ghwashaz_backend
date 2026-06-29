package Ascenso.sytem.finance.repository;

import Ascenso.sytem.finance.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Expense Repository - extends JpaSpecificationExecutor for dynamic queries
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID>, JpaSpecificationExecutor<Expense> {
}
