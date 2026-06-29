package Ascenso.sytem.customer.entity;

import Ascenso.sytem.common.enums.CustomerType;
import Ascenso.sytem.sale.entity.Sale;
import Ascenso.sytem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "customers",
        indexes = {
                @Index(name = "idx_customer_name", columnList = "full_name"),
                @Index(name = "idx_customer_phone", columnList = "phone_number")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {

    @Column(name ="full_name", length=100)
    private String fullName;

    @Column(name = "phone_number", length =20, unique = true)
    private String phoneNumber;

    @Column(length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type", nullable = false)
    private CustomerType customerType;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(length = 255)
    private String notes;

    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "customer", fetch =FetchType.LAZY)
    private List<Sale> sales = new ArrayList<>();
}
