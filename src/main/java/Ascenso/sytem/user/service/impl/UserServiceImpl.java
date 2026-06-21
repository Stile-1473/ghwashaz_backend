package Ascenso.sytem.user.service.impl;

import Ascenso.sytem.common.exception.BadRequestException;
import Ascenso.sytem.user.dto.*;
import Ascenso.sytem.user.entity.Role;
import Ascenso.sytem.user.entity.User;
import Ascenso.sytem.user.mapper.UserMapper;
import Ascenso.sytem.user.repository.RoleRepository;
import Ascenso.sytem.user.repository.UserRepository;
import Ascenso.sytem.user.service.UserServiceContract;
import Ascenso.sytem.user.specification.UserSpecification;
import Ascenso.sytem.user.validator.UserValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserServiceContract {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final UserValidator userValidator;

    @Override
    public UserResponseDto createUser(CreateUserRequestDto requestDto) {
        userValidator.validateUniquePhoneNumber(requestDto.getPhoneNumber());

        Set<Role> roles = userValidator.validateRoles(requestDto.getRoleIds());

        User user = userMapper.toEntity(requestDto);

        user.setPasswordHash(passwordEncoder.encode(requestDto.getPassword()));

        user.setRoles(roles);

        user.setEnabled(true);

        user.setLocked(false);

        user.setAccountExpired(false);

        user.setCredentialsExpired(false);

        User saveUser = userRepository.save(user);

        log.info("User {} created successfully",saveUser.getPhoneNumber());

        return userMapper.toResponse(saveUser);

    }

    @Override
    public UserResponseDto getUserById(UUID id) {
        User user = userValidator.validateUserExists(id);

        return userMapper.toResponse(user);
    }

    @Override
    public void resetPassword(UUID id, ResetPasswordRequestDto requestDto){



        User user =  userValidator.validateUserExists(id);

        user.setPasswordHash(passwordEncoder.encode(requestDto.getNewPassword()));

        userRepository.save(user);

        log.info("Password reset for user {}",user.getPhoneNumber());
    }

    @Override
    public Page<UserResponseDto> getUsers(String search, Boolean enabled, Pageable pageable) {
        Page<User> users = userRepository.findAll(UserSpecification.search(
                search,
                enabled
        ),pageable);

        return users.map(userMapper::toResponse);
    }

    @Override
    public UserResponseDto updateUser(UUID id, UpdateUserRequestDto requestDto) {
        User user =  userValidator.validateUserExists(id);

        if(!user.getPhoneNumber().equals(requestDto.getPhoneNumber())){
            userValidator.validateUniquePhoneNumber(requestDto.getPhoneNumber());
        }

        Set<Role> roles = userValidator.validateRoles(requestDto.getRoleIds());

        userMapper.updateEntity(requestDto,user);

        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        log.info("User {} updated",savedUser.getPhoneNumber());

        return  userMapper.toResponse(savedUser);
    }

    @Override
    public void changePassword(UUID id, ChangePasswordRequestDto requestDto) {
        User user = userValidator.validateUserExists(id);

        if(!passwordEncoder.matches(requestDto.getOldPassword(),user.getPasswordHash())){
            throw new BadRequestException(
                    "Old password is incorrect"
            );

        }

        userValidator.validatePasswordConfirmation(requestDto.getNewPassword(), requestDto.getConfirmPassword());

        user.setPasswordHash(passwordEncoder.encode(requestDto.getNewPassword()));

        userRepository.save(user);

        log.info("Password changed for user {}",user.getPhoneNumber());
    }

    @Override
    public void deactivateUser(UUID id) {
        userValidator.validateSelfDisable(id);

        User user = userValidator.validateUserExists(id);

        user.setEnabled(false);

        userRepository.save(user);

        log.info("User {} disabled",user.getPhoneNumber());
    }

    @Override
    public void activateUser(UUID id){
        User user = userValidator.validateUserExists(id);

        user.setEnabled(true);

        userRepository.save(user);

    log.info("User {} activated ",user.getPhoneNumber());
    }
}
