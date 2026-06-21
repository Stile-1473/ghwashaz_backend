package Ascenso.sytem.user.mapper;

import Ascenso.sytem.common.mapper.MapperConfiguration;
import Ascenso.sytem.user.dto.CreateUserRequestDto;
import Ascenso.sytem.user.dto.UpdateUserRequestDto;
import Ascenso.sytem.user.dto.UserResponseDto;
import Ascenso.sytem.user.entity.Permission;
import Ascenso.sytem.user.entity.Role;
import Ascenso.sytem.user.entity.User;
import org.mapstruct.*;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = MapperConfiguration.class)
public interface UserMapper {
    @Mapping(target = "passwordHash",ignore = true)
    @Mapping(target = "roles",ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "locked", ignore = true)
    @Mapping(target = "credentialsExpired", ignore = true)
    @Mapping(target = "accountExpired", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    User toEntity(CreateUserRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    void updateEntity(UpdateUserRequestDto dto,
                      @MappingTarget User user);



    @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
    @Mapping(target = "permissions", expression = "java(mapPermissions(user.getRoles()))")
    UserResponseDto toResponse(User user);

    default Set<String> mapRoles(Set<Role> roles) {

        return roles.stream()

                .map(Role::getName)

                .collect(Collectors.toSet());

    }

    default Set<String> mapPermissions(Set<Role> roles) {

        return roles.stream()

                .flatMap(role -> role.getPermissions().stream())

                .map(Permission::getName)

                .collect(Collectors.toSet());

    }



}
