package Ascenso.sytem.product.repository;

import Ascenso.sytem.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> , JpaSpecificationExecutor<Product> {

    boolean existsBySku(String sku);

    boolean existsByBarcode(String barcode);

    boolean existsByNameIgnoreCase(String name);

    Optional<Product> findBySku(String sku);

}
