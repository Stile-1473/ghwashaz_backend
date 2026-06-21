package Ascenso.sytem.user.mapper;

import Ascenso.sytem.common.mapper.MapperConfiguration;
import Ascenso.sytem.user.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfiguration.class)
public interface PermissionMapper {

    default String toString(Permission permission) {

        return permission.getName();

    }

}
