package Ascenso.sytem.supplier.mapper;

import Ascenso.sytem.common.mapper.MapperConfiguration;
import Ascenso.sytem.supplier.dto.CreateSupplierRequestDto;
import Ascenso.sytem.supplier.dto.SupplierResponseDto;
import Ascenso.sytem.supplier.dto.UpdateSupplierRequestDto;
import Ascenso.sytem.supplier.entity.Supplier;
import org.mapstruct.*;

@Mapper(componentModel = "spring", config = MapperConfiguration.class)
public interface SupplierMapper {

    @Mapping(target = "active",ignore = true)
    Supplier toEntity(CreateSupplierRequestDto dto);

    SupplierResponseDto toResponse(Supplier supplier);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "syncStatus", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active",ignore = true)

    void updateEntity(UpdateSupplierRequestDto dto, @MappingTarget Supplier supplier);

}
