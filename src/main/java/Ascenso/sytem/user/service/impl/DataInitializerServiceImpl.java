package Ascenso.sytem.user.service.impl;

import Ascenso.sytem.common.constants.Permissions;
import Ascenso.sytem.common.constants.Roles;
import Ascenso.sytem.user.entity.Permission;
import Ascenso.sytem.user.entity.Role;
import Ascenso.sytem.user.entity.User;
import Ascenso.sytem.user.repository.PermissionRepository;
import Ascenso.sytem.user.repository.RoleRepository;
import Ascenso.sytem.user.repository.UserRepository;
import Ascenso.sytem.user.service.DataIntializerServiceContract;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DataInitializerServiceImpl implements DataIntializerServiceContract {

    private final PermissionRepository permissionRepository;

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void intialize() {

        createPermissions();

        createRoles();

        createDefaultOwner();

    }


    private void createPermissions(){

        List<String> permissions = List.of(

                Permissions.PRODUCT_VIEW,
                Permissions.PRODUCT_CREATE,
                Permissions.PRODUCT_UPDATE,
                Permissions.PRODUCT_DELETE,

                Permissions.CATEGORY_VIEW,
                Permissions.CATEGORY_MANAGE,

                Permissions.CUSTOMER_VIEW,
                Permissions.CUSTOMER_CREATE,

                Permissions.SALE_CREATE,
                Permissions.SALE_VIEW,
                Permissions.SALE_VOID,

                Permissions.PURCHASE_CREATE,
                Permissions.PURCHASE_APPROVE,

                Permissions.INVENTORY_VIEW,
                Permissions.INVENTORY_ADJUST,

                Permissions.CASH_VIEW,
                Permissions.CASH_WITHDRAW,

                Permissions.REPORT_VIEW,

                Permissions.USER_MANAGE,

                Permissions.SETTINGS_MANAGE,

                Permissions.AUDIT_VIEW
        );

        for(String permissionName : permissions){
            permissionRepository.findByName(permissionName)
                    .orElseGet(()->{
                        Permission permission = Permission.builder()
                                .name(permissionName)
                                .description(permissionName)
                                .build();
                        return permissionRepository.save(permission);
                    });
        }
        log.info("Permissions initialized");

    }

    private void createRoles(){
        createOwnerRole();

        createCashierRole();
    }

    private void createOwnerRole(){
        Set<Permission> permissions = new HashSet<>(permissionRepository.findAll());


        if(roleRepository.findByName(Roles.OWNER).isPresent()){
            Role existingOwner = roleRepository.findByName(Roles.OWNER).orElseThrow();
            existingOwner.setPermissions(permissions);
            roleRepository.save(existingOwner);
            log.info("OWNER role permissions synced");
            return;
        }

        Role owner =  Role.builder()
                .name(Roles.OWNER)
                .description("System Owner")
                .permissions(permissions)
                .build();

        roleRepository.save(owner);

        log.info("OWNER role created");
    }





    private void createCashierRole() {

        Set<Permission> permissions = new HashSet<>(Set.of(


                permissionRepository.findByName(Permissions.PRODUCT_VIEW).orElseThrow(),

                permissionRepository.findByName(Permissions.CUSTOMER_VIEW).orElseThrow(),

                permissionRepository.findByName(Permissions.CUSTOMER_CREATE).orElseThrow(),

                permissionRepository.findByName(Permissions.SALE_CREATE).orElseThrow(),

                permissionRepository.findByName(Permissions.SALE_VIEW).orElseThrow(),

                permissionRepository.findByName(Permissions.INVENTORY_VIEW).orElseThrow(),

                permissionRepository.findByName(Permissions.CASH_VIEW).orElseThrow()
        ));

        if (roleRepository.findByName(Roles.CASHIER).isPresent()) {
            Role existingCashier = roleRepository.findByName(Roles.CASHIER).orElseThrow();
            existingCashier.setPermissions(permissions);
            roleRepository.save(existingCashier);
            log.info("CASHIER role permissions synced");
            return;
        }

        Role cashier = Role.builder()
                .name(Roles.CASHIER)
                .description("Store Cashier")
                .permissions(permissions)
                .build();

        roleRepository.save(cashier);

        log.info("CASHIER role created");


    }



    private void createDefaultOwner(){

        if(userRepository.findByPhoneNumber("+263786828855").isPresent()){
            return;
        }

        Role ownerRole = roleRepository.findByName(Roles.OWNER).orElseThrow();

        User owner = User.builder()
                .fullName("Kudakwashe Chishowerere")
                .passwordHash(passwordEncoder.encode("kuda"))
                .phoneNumber("+263786828855")
                .enabled(true)
                .accountExpired(false)
                .locked(false)
                .credentialsExpired(false)
                .roles(Set.of(ownerRole))

                .build();

        userRepository.save(owner);

        log.info("Default admin created");

    }
}
