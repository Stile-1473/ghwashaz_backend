package Ascenso.sytem.sale.repository;

import Ascenso.sytem.sale.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SaleRepository extends JpaRepository<Sale, UUID>, JpaSpecificationExecutor<Sale> {

    Optional<Sale> findBySaleNumber(String saleNumber);
    
    @Query("SELECT s FROM Sale s LEFT JOIN FETCH s.items WHERE s.id = :id")
    Optional<Sale> findByIdWithItems(@Param("id") UUID id);
    
    @Query("SELECT s FROM Sale s LEFT JOIN FETCH s.payments WHERE s.id = :id")
    Optional<Sale> findByIdWithPayments(@Param("id") UUID id);
    
}
