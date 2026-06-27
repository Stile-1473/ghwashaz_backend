package Ascenso.sytem.supplier.repository;

import Ascenso.sytem.supplier.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface SupplierRepository extends
        JpaRepository<Supplier, UUID>,
        JpaSpecificationExecutor<Supplier> {

    boolean existsByCompanyNameIgnoreCase(String companyName);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<Supplier> findByCompanyNameIgnoreCase(String companyName);

}