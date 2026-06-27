package Ascenso.sytem.supplier.specification;


import Ascenso.sytem.supplier.entity.Supplier;
import org.springframework.data.jpa.domain.Specification;

public final class SupplierSpecification {

    private SupplierSpecification(){}

    public static Specification<Supplier> search(

            String search,

            Boolean active

    ){

        return (root, query, cb)->{

            var predicate = cb.conjunction();

            if(search != null && !search.isBlank()){

                String keyword="%"+search.toLowerCase()+"%";

                predicate = cb.and(

                        predicate,

                        cb.or(

                                cb.like(
                                        cb.lower(root.get("companyName")),
                                        keyword
                                ),

                                cb.like(
                                        cb.lower(root.get("contactPerson")),
                                        keyword
                                ),

                                cb.like(
                                        cb.lower(root.get("phoneNumber")),
                                        keyword
                                )

                        )

                );

            }

            if(active != null){

                predicate = cb.and(

                        predicate,

                        cb.equal(
                                root.get("active"),
                                active
                        )

                );

            }

            return predicate;

        };

    }

}