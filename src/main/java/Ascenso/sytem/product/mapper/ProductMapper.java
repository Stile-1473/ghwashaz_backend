package Ascenso.sytem.product.mapper;

import Ascenso.sytem.common.mapper.MapperConfiguration;
import Ascenso.sytem.product.dto.CreateProductRequestDto;
import Ascenso.sytem.product.dto.ProductResponseDto;
import Ascenso.sytem.product.dto.UpdateProductRequestDto;
import Ascenso.sytem.product.entity.Product;
import org.mapstruct.*;

@Mapper(config = MapperConfiguration.class)
public interface ProductMapper {

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "sku", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "syncStatus", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    Product toEntity(CreateProductRequestDto dto);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ProductResponseDto toResponse(Product product);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sku", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "syncStatus", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    void updateEntity(
            UpdateProductRequestDto dto,
            @MappingTarget Product product
    );


}
