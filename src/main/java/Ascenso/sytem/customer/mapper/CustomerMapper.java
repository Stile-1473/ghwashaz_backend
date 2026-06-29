package Ascenso.sytem.customer.mapper;

import Ascenso.sytem.common.mapper.MapperConfiguration;
import Ascenso.sytem.customer.dto.CreateCustomerRequestDto;
import Ascenso.sytem.customer.dto.CustomerResponseDto;
import Ascenso.sytem.customer.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = "spring", 
    config = MapperConfiguration.class, 
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface CustomerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "sales", ignore = true)
    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "syncStatus", ignore = true)
    @Mapping(target = "email", ignore = true)
    Customer toEntity(CreateCustomerRequestDto dto);

    @Mapping(target = "totalSpent", ignore = true)
    @Mapping(target = "totalPurchases", ignore = true)
    @Mapping(target = "lastPurchaseDate", ignore = true)
    CustomerResponseDto toResponse(Customer customer);

    List<CustomerResponseDto> toResponseList(List<Customer> customers);
}
