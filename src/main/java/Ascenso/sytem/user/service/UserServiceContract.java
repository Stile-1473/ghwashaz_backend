package Ascenso.sytem.user.service;

import Ascenso.sytem.user.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserServiceContract {

    UserResponseDto createUser(CreateUserRequestDto requestDto);

    UserResponseDto getUserById(UUID id);



    void resetPassword(
            UUID id,
            ResetPasswordRequestDto requestDto
    );

    Page<UserResponseDto> getUsers(
            String search,
            Boolean active,
            Pageable pageable
    );

    UserResponseDto updateUser(
            UUID id,
            UpdateUserRequestDto requestDto
    );

     void changePassword(

             ChangePasswordRequestDto requestDto
     ) throws IllegalAccessException;

     void deactivateUser(
             UUID id
     );


    void activateUser(UUID id);
}
