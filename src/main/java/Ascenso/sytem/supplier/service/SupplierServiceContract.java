package Ascenso.sytem.supplier.service;

import Ascenso.sytem.supplier.dto.CreateSupplierRequestDto;
import Ascenso.sytem.supplier.dto.SupplierResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SupplierServiceContract {

    SupplierResponseDto createSupplier(CreateSupplierRequestDto createSupplierRequestDto);

    SupplierResponseDto getSupplier(UUID id);

    Page<SupplierResponseDto> getSuppliers(
      String search,
      Boolean active,
      Pageable pageable
    );

    SupplierResponseDto updateSupplier(
            UUID id,
            CreateSupplierRequestDto requestDto
    );



}
