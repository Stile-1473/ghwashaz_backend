package Ascenso.sytem.common.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE
)
public interface MapperConfiguration {






}
