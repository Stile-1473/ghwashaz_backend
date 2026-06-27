package Ascenso.sytem.supplier.specification;

import Ascenso.sytem.supplier.entity.Purchase;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Purchase filtering specification.
 */
public final class PurchaseSpecification {

    private PurchaseSpecification() {
    }

    public static Specification<Purchase> search(
            String search,
            UUID supplierId,
            String status
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (supplierId != null) {
                // supplier is a @ManyToOne in Purchase
                predicates.add(cb.equal(root.get("supplier").get("id"), supplierId));
            }

            if (status != null && !status.isBlank()) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            if (search != null && !search.isBlank()) {
                String like = "%" + search.trim().toLowerCase() + "%";
                predicates.add(
                        cb.like(cb.lower(root.get("purchaseNumber")), like)
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

