package Ascenso.sytem.user.service;

import Ascenso.sytem.user.dto.ChangePasswordRequestDto;
import Ascenso.sytem.user.dto.CreateUserRequestDto;
import Ascenso.sytem.user.dto.UpdateUserRequestDto;
import Ascenso.sytem.user.dto.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserServiceContract {

    UserResponseDto createUser(CreateUserRequestDto requestDto);

    UserResponseDto getUserById(UUID id);

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
             UUID id,
             ChangePasswordRequestDto requestDto
     );

     void deactivateUser(
             UUID id
     );


}
