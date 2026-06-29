package Ascenso.sytem.sale.mapper;

import Ascenso.sytem.common.mapper.MapperConfiguration;
import Ascenso.sytem.sale.dto.SaleResponseDto;
import Ascenso.sytem.sale.entity.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", config = MapperConfiguration.class)
public interface SaleMapper {

    @Mapping(source = "cashier.fullName", target = "cashierName")
    @Mapping(source = "customer.fullName", target = "customerName")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createdAt", target = "createdAt")
    SaleResponseDto toResponse(Sale sale);

}
