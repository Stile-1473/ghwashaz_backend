package Ascenso.sytem.sale.refund.repository;

import Ascenso.sytem.sale.refund.entity.SaleReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReturnRepository extends JpaRepository<SaleReturn, UUID> {
}
