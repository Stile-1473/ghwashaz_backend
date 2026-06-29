package Ascenso.sytem.customer.repository;

import Ascenso.sytem.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID>, JpaSpecificationExecutor<Customer> {

    Optional<Customer> findByPhoneNumber(String phoneNumber);
    
    boolean existsByPhoneNumber(String phoneNumber);
    
    Page<Customer> findTopByActiveTrueOrderByCreatedAtDesc(Pageable pageable);
    
}
