package Ascenso.sytem.audit.specification;

import Ascenso.sytem.audit.entity.AuditLogs;
import Ascenso.sytem.common.enums.AuditActionType;
import Ascenso.sytem.common.enums.AuditModule;
import org.springframework.data.jpa.domain.Specification;

public final class AuditSpecification {

    private AuditSpecification(){

    }

    public  static Specification<AuditLogs> filter(
          AuditModule module,
          AuditActionType actionType,
          String search
    ){
        return (root,query,cb) -> {
            var predicate = cb.conjunction();

            if(module != null){

                predicate = cb.and(
                        predicate,
                        cb.equal(root.get("module"),module)
                );
            }

            if(search != null && !search.isBlank()){
                String keyword = "%" + search.toLowerCase() + "%";

                predicate =cb.and(
                        predicate,
                        cb.or(
                                cb.like(
                                        cb.lower(root.get("fullName")),
                                        keyword
                                ),

                                cb.like(
                                        cb.lower(root.get("phoneNumber")),
                                        keyword
                                ),
                                cb.like(
                                        cb.lower(root.get("description")),
                                        keyword
                                )
                        )
                );
            }
            return predicate;

        };
    }

}
