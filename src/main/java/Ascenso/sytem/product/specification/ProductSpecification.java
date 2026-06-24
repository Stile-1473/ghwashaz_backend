package Ascenso.sytem.product.specification;

import Ascenso.sytem.product.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public final class ProductSpecification {

    private ProductSpecification(){}

    public static Specification<Product> search(

            String search,

            UUID categoryId,

            Boolean active

    ){

        return (root, query, cb) -> {

            var predicate = cb.conjunction();

            if(search != null && !search.isBlank()){

                String keyword =
                        "%" + search.toLowerCase() + "%";

                predicate = cb.and(
                        predicate,
                        cb.or(

                                cb.like(
                                        cb.lower(root.get("name")),
                                        keyword
                                ),

                                cb.like(
                                        cb.lower(root.get("sku")),
                                        keyword
                                ),

                                cb.like(
                                        cb.lower(root.get("barcode")),
                                        keyword
                                )

                        )
                );

            }

            if(categoryId != null){

                predicate = cb.and(
                        predicate,
                        cb.equal(
                                root.get("category").get("id"),
                                categoryId
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