package Ascenso.sytem.sale.specification;

import Ascenso.sytem.common.enums.PaymentMethod;
import Ascenso.sytem.common.enums.SaleStatus;
import Ascenso.sytem.sale.entity.Sale;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class SaleSpecification {


    public static Specification<Sale> search(
            String search,
            UUID cashierId,
            UUID customerId,
            SaleStatus status,
            LocalDate fromDate,
            LocalDate toDate
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Search by sale number
            if (search != null && !search.isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("saleNumber")),
                        "%" + search.toLowerCase() + "%"
                ));
            }

            // Filter by cashier
            if (cashierId != null) {
                predicates.add(cb.equal(
                        root.get("cashier").get("id"),
                        cashierId
                ));
            }

            // Filter by customer
            if (customerId != null) {
                predicates.add(cb.equal(
                        root.get("customer").get("id"),
                        customerId
                ));
            }

            // Filter by status
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
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

}
