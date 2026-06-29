package Ascenso.sytem.user.mapper;

import Ascenso.sytem.common.mapper.MapperConfiguration;
import Ascenso.sytem.user.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = MapperConfiguration.class)
public interface RoleMapper {

    default String toString(Role role) {
        return role != null ? role.getName() : null;
    }
}
