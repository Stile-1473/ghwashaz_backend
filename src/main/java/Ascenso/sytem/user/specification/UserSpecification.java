package Ascenso.sytem.user.specification;

import Ascenso.sytem.user.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    private UserSpecification(){

    }

    public  static Specification<User> search(String search,Boolean enabled){
        return (root,query,cb) -> {
            var predicate = cb.conjunction();

            if(search != null && !search.isBlank()){

                String value = "%" + search.toLowerCase() + "%";

                predicate = cb.and(
                        predicate,
                        cb.or(
                                cb.like(
                                        cb.lower(root.get("fullName")),
                                        value
                                ),
                                cb.like(
                                        cb.lower(root.get("phoneNumber")),
                                        value
                                )
                        )
                );
            }

            if(enabled != null ){
                predicate = cb.and(
                        predicate,
                        cb.equal(
                                root.get("enabled"),
                                enabled
                        )
                );

            }

            return  predicate;
        };


    }
}
