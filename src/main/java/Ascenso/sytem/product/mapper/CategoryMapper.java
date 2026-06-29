package Ascenso.sytem.product.mapper;

import Ascenso.sytem.common.mapper.MapperConfiguration;
import Ascenso.sytem.product.dto.CategoryResponseDto;
import Ascenso.sytem.product.dto.CreateCategoryRequestDto;
import Ascenso.sytem.product.dto.UpdateCategoryRequestDto;
import Ascenso.sytem.product.entity.Category;
import org.mapstruct.*;

@Mapper(componentModel = "spring", config = MapperConfiguration.class)
public interface CategoryMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "syncStatus", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    Category toEntity(CreateCategoryRequestDto dto);


    CategoryResponseDto toResponse(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)

    @Mapping(target = "syncStatus", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void update(
            UpdateCategoryRequestDto dto,
            @MappingTarget Category category
    );



}
