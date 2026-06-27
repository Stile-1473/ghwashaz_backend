package Ascenso.sytem.supplier.service;

import Ascenso.sytem.common.response.PageResponse;
import Ascenso.sytem.supplier.dto.CreateSupplierRequestDto;
import Ascenso.sytem.supplier.dto.SupplierResponseDto;
import Ascenso.sytem.supplier.dto.UpdateSupplierRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SupplierServiceContract {

    SupplierResponseDto createSupplier(CreateSupplierRequestDto createSupplierRequestDto);

    SupplierResponseDto getSupplier(UUID id);

    PageResponse<SupplierResponseDto> getSuppliers(
      String search,
      Boolean active,
      Pageable pageable
    );




    SupplierResponseDto updateSupplier(
            UUID id,
            UpdateSupplierRequestDto requestDto
    );

    void activateSupplier(UUID id);

    void deactivateSupplier(UUID id);

}
