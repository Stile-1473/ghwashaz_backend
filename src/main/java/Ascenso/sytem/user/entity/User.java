package Ascenso.sytem.user.entity;

import Ascenso.sytem.common.enums.Role;
import Ascenso.sytem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name="users",
        indexes = {
                @Index(name = "idx_phone",columnList = "phoneNumber")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    private String fullName;

    private String phoneNumber;

    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean active = true;

    private LocalDateTime lastLogin;



}
