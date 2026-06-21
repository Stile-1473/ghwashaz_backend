package Ascenso.sytem.user.controller;

import Ascenso.sytem.common.response.ApiResponse;
import Ascenso.sytem.user.dto.ChangePasswordRequestDto;
import Ascenso.sytem.user.dto.CreateUserRequestDto;
import Ascenso.sytem.user.dto.UpdateUserRequestDto;
import Ascenso.sytem.user.dto.UserResponseDto;
import Ascenso.sytem.user.service.UserServiceContract;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserServiceContract userService;


    //create user APi

    @PostMapping
    @PreAuthorize("hasAuthority(T(Ascenso.system.common.constants.Permissions).USER_MANAGE)")
    public ResponseEntity<ApiResponse<UserResponseDto>> create(
            @Valid
            @RequestBody CreateUserRequestDto dto){

        return ResponseEntity.ok(
                ApiResponse.<UserResponseDto>builder()
                        .success(true)
                        .message("User created successfully")
                        .data(userService.createUser(dto))
                        .build()
        );
    }


    //Get user by id API

    @GetMapping("/{id}")
    @PreAuthorize(
            "hasAuthority(T(Ascenso.system.common.constants.Permissions).USER_MANAGE)"
    )
    public ResponseEntity<ApiResponse<UserResponseDto>> getUser(
            @PathVariable
            UUID id
    ){

        return ResponseEntity.ok(
                ApiResponse.<UserResponseDto>builder()
                        .success(true)
                        .message("User fetched successfully")
                        .data(userService.getUserById(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize(
            "hasAuthority(T(Ascenso.system.common.constants.Permissions).USER_MANAGE)"
    )
    public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(
            @Valid
            @PathVariable UUID id,
            @RequestBody UpdateUserRequestDto dto
            ){

        return ResponseEntity.ok(
                ApiResponse.<UserResponseDto>builder()
                        .success(true)
                        .message("User updated successfully")
                        .data(userService.updateUser(id,dto))
                        .build()
        );

    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize(
            "hasAuthority(T(Ascenso.system.common.constants.Permissions).USER_MANAGE)"
    )

    public ResponseEntity<ApiResponse> activateUser(@Valid @PathVariable UUID id) {

        userService.activateUser(id);
      return  ResponseEntity.ok( ApiResponse.builder()
                .success(true)
                .message("User enabled successfully")
                .data(null)
                .build()

      );



    }


    @PatchMapping("/{id}/deactivate")
    @PreAuthorize(
            "hasAuthority(T(Ascenso.system.common.constants.Permissions).USER_MANAGE)"
    )

    public ResponseEntity<ApiResponse> deactivateUser(@Valid @PathVariable UUID id) {

        userService.deactivateUser(id);
        return  ResponseEntity.ok( ApiResponse.builder()
                .success(true)
                .message("User disabled successfully")
                .data(null)
                .build()

        );



    }



    @PatchMapping("/{id}/change-password")
    @PreAuthorize(
            "hasAuthority(T(Ascenso.system.common.constants.Permissions).USER_MANAGE)"
    )

    public ResponseEntity<ApiResponse> activateUser(@Valid @PathVariable UUID id, @RequestBody ChangePasswordRequestDto dto) {

        userService.changePassword(id,dto);
        return  ResponseEntity.ok( ApiResponse.builder()
                .success(true)
                .message("User enabled successfully")
                .data(null)
                .build()

        );



    }



}
