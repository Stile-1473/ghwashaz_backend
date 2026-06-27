package Ascenso.sytem.supplier.repository;

import Ascenso.sytem.supplier.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, UUID>, JpaSpecificationExecutor<Purchase> {
}

