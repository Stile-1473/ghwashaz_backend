package Ascenso.sytem.finance.specification;

import Ascenso.sytem.finance.entity.CashTransaction;
import Ascenso.sytem.common.enums.CashTransactionType;
import Ascenso.sytem.finance.entity.Expense;
import Ascenso.sytem.common.enums.ExpenseCategory;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class FinanceSpecification {


    public static Specification<Expense> expenseSearch(
            String search,
            ExpenseCategory category,
            LocalDate fromDate,
            LocalDate toDate
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Search by description
            if (search != null && !search.isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("description")),
                        "%" + search.toLowerCase() + "%"
                ));
            }

            // Filter by category
            if (category != null) {
                predicates.add(cb.equal(root.get("category"), category));
            }

            // Filter from date
            if (fromDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("createdAt"),
                        fromDate.atStartOfDay()
                ));
            }


            // Filter to date
            if (toDate != null) {
                predicates.add(cb.lessThanOrEqualTo(
                        root.get("createdAt"),
                        toDate.atTime(23, 59, 59)
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }


    public static Specification<CashTransaction> transactionSearch(
            String search,
            CashTransactionType type,
            LocalDate fromDate,
            LocalDate toDate
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (search != null && !search.isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("transactionNumber")),
                        "%" + search.toLowerCase() + "%"
                ));
            }

            if (type != null) {
                predicates.add(cb.equal(root.get("transactionType"), type));
            }

            if (fromDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("createdAt"),
                        fromDate.atStartOfDay()
                ));
            }

            if (toDate != null) {
                predicates.add(cb.lessThanOrEqualTo(
                        root.get("createdAt"),
                        toDate.atTime(23, 59, 59)
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
