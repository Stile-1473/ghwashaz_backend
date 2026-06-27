package Ascenso.sytem.inventory.specification;

import Ascenso.sytem.inventory.entity.Inventory;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class InventorySpecification {

    private InventorySpecification() {}

    public static Specification<Inventory> search(String search, Boolean lowStock) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (lowStock != null && lowStock) {
                // lowStock = quantityOnHand <= minimumStockLevel
                predicates.add(cb.lessThanOrEqualTo(
                        root.get("quantityOnHand"),
                        root.get("product").get("minimumStockLevel")
                ));
            }

            if (search != null && !search.isBlank()) {
                String like = "%" + search.trim().toLowerCase() + "%";
                predicates.add(
                        cb.or(
                                cb.like(cb.lower(root.get("product").get("name")), like),
                                cb.like(cb.lower(root.get("product").get("sku")), like),
                                cb.like(cb.lower(root.get("product").get("barcode")), like)
                        )
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

