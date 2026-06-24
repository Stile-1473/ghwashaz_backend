package Ascenso.sytem.product.specification;


import Ascenso.sytem.product.entity.Category;
import org.springframework.data.jpa.domain.Specification;

public final class CategorySpecification {

    private CategorySpecification(){}

    public static Specification<Category> search(

            String search,

            Boolean active

    ){

        return (root, query, cb) ->{

            var predicate = cb.conjunction();

            if(search != null && !search.isBlank()){

                String keyword = "%" + search.toLowerCase() + "%";

                predicate = cb.and(

                        predicate,

                        cb.like(

                                cb.lower(root.get("name")),

                                keyword

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