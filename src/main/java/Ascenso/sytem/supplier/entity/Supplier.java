package Ascenso.sytem.supplier.entity;

import Ascenso.sytem.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@Entity
@Table(
        name = "suppliers",
        indexes = {
                @Index(
                       name = "idx_supplier_company",
                        columnList = "companyName"
                ),
                @Index(
                        name = "idx_supplier_phone",
                        columnList = "phoneNumber"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Supplier extends BaseEntity {

    @Column(nullable = false,unique = true)
    private String companyName;


    @Column(nullable = false)
    private String contactPerson;

    @Column(nullable = false)
    private String phoneNumber;


    private String alternativePhoneNumber;

    private String email;

    @Column(length = 1000)
    private String address;

    private String city;

    private String country;

    @Column(length = 1000)
    private String notes;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;


}
