package Ascenso.sytem.customer.specification;

import Ascenso.sytem.common.enums.CustomerType;
import Ascenso.sytem.customer.entity.Customer;
import org.springframework.data.jpa.domain.Specification;

public final class CustomerSpecification {

    private CustomerSpecification(){}

    public static Specification<Customer> search(
            String search,
            CustomerType customerType,
            Boolean active
    ){
        return (root, query, cb)->{
            var predicate = cb.conjunction();

            if(search != null && !search.isBlank()){
                String keyword = "%" + search.toLowerCase() + "%";
                predicate = cb.and(
                        predicate,
                        cb.or(
                                cb.like(cb.lower(root.get("fullName")), keyword),
                                cb.like(cb.lower(root.get("phoneNumber")), keyword)
                        )
                );
            }

            if(customerType != null){
                predicate = cb.and(
                        predicate,
                        cb.equal(root.get("customerType"), customerType)
                );
            }

            if(active != null){
                predicate = cb.and(
                        predicate,
                        cb.equal(root.get("active"), active)
                );
            }

            return predicate;
        };
    }
}
