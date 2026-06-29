package Ascenso.sytem.finance.mapper;

import Ascenso.sytem.common.mapper.MapperConfiguration;
import Ascenso.sytem.finance.dto.ExpenseResponseDto;
import Ascenso.sytem.finance.entity.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", config = MapperConfiguration.class)
public interface ExpenseMapper {


    @Mapping(source = "recordedBy.fullName", target = "recordedBy")
    ExpenseResponseDto toResponse(Expense expense);
}
