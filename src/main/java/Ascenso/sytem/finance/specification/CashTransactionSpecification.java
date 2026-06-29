package Ascenso.sytem.finance.specification;

import Ascenso.sytem.finance.dto.CashTransactionQueryDto;
import Ascenso.sytem.finance.entity.CashTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CashTransactionSpecification {

    public static Specification<CashTransaction> filter(CashTransactionQueryDto query) {
        return (Root<CashTransaction> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (query != null) {
                if (query.getTransactionType() != null) {
                    predicates.add(cb.equal(root.get("transactionType"), query.getTransactionType()));
                }
                if (query.getCashIn() != null) {
                    predicates.add(cb.equal(root.get("cashIn"), query.getCashIn()));
                }
                if (query.getSource() != null) {
                    predicates.add(cb.equal(root.get("source"), query.getSource()));
                }
                if (query.getStartDate() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), query.getStartDate()));
                }
                if (query.getEndDate() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), query.getEndDate()));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
