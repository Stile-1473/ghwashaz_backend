package Ascenso.sytem.product.service;

import Ascenso.sytem.common.response.PageResponse;
import Ascenso.sytem.product.dto.CategoryResponseDto;
import Ascenso.sytem.product.dto.CreateCategoryRequestDto;
import Ascenso.sytem.product.dto.UpdateCategoryRequestDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CategoryServiceContract {


    CategoryResponseDto createCategory(
            CreateCategoryRequestDto request
    );

    CategoryResponseDto updateCategory(
            UUID id,
            UpdateCategoryRequestDto request
    );


    CategoryResponseDto getCategory(
            UUID id
    );

    PageResponse<CategoryResponseDto> getCategories(
            String search,
            Boolean active,
            Pageable pageable
    );






    void deactivateCategory(
            UUID id
    );

    void activateCategory(UUID id);
}