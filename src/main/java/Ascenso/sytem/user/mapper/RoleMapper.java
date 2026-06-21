package Ascenso.sytem.user.mapper;

import Ascenso.sytem.user.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    default String toString(Role role) {

        return role.getName();

    }

}