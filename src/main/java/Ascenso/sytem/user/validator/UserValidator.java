package Ascenso.sytem.user.validator;

import Ascenso.sytem.common.exception.BadRequestException;
import Ascenso.sytem.common.exception.ResourceNotFoundException;
import Ascenso.sytem.security.util.SecurityUtils;
import Ascenso.sytem.user.entity.Role;
import Ascenso.sytem.user.entity.User;
import Ascenso.sytem.user.mapper.UserMapper;
import Ascenso.sytem.user.repository.RoleRepository;
import Ascenso.sytem.user.repository.UserRepository;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;


    public void validateUniquePhoneNumber(String phoneNumber) {

        if(userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new BadRequestException(
                    "Phone number already exists"
            );

        }

    }

    public Set<Role> validateRoles(Set<UUID> roleIds){

        Set<Role> roles = new HashSet<>(roleRepository.findAllById(roleIds));

        if(roles.size() != roleIds.size()){
            throw new BadRequestException(
                    "One or more roles do not exist"
            );


        }

        return roles;

    }

    public User validateUserExists(UUID id){
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(
                        "User not found"
                )
                );
    }


    public   void validateSelfDisable(UUID userId){
        UUID currentUser = SecurityUtils.getCurrentUserId();

        if(currentUser.equals(userId)){
            throw  new BadRequestException(
                    "You can not deactivate your own account"
            );
        }
    }

    public void validatePasswordConfirmation(String password,String confirmPassword){
        if(!password.equals(confirmPassword)){
            throw new BadRequestException(
                    "Passwords do not match"
            );
        }
    }

}
