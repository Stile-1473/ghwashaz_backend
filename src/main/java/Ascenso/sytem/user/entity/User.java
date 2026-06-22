package Ascenso.sytem.user.entity;

import Ascenso.sytem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
@Builder
public class User extends BaseEntity {

    private String fullName;

    private String phoneNumber;

    private String passwordHash;

   @ManyToMany(fetch = FetchType.EAGER)
   @JoinTable(
           name = "user_roles",
           joinColumns = @JoinColumn(name = "user_id"),
           inverseJoinColumns = @JoinColumn(name="role_id")
   )
   @Builder.Default
   private Set<Role> roles = new HashSet<>();

  private   Boolean enabled;

  private   Boolean locked;

   private Boolean credentialsExpired;

    private Boolean accountExpired;



    private LocalDateTime lastLogin;



}
