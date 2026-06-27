package Ascenso.sytem.supplier.repository;

import Ascenso.sytem.supplier.entity.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, UUID> {
}
