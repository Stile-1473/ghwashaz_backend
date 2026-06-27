package Ascenso.sytem.inventory.repository;

import Ascenso.sytem.inventory.entity.Inventory;
import Ascenso.sytem.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import java.util.Optional;
import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, UUID>, JpaSpecificationExecutor<Inventory> {

  Optional<Inventory> findByProduct(Product product);
}
