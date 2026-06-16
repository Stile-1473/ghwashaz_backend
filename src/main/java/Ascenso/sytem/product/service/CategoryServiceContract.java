package Ascenso.sytem.product.service;

import Ascenso.sytem.product.dto.CategoryResponseDto;
import Ascenso.sytem.product.dto.CreateCategoryRequestDto;

import java.util.List;
import java.util.UUID;

public interface CategoryServiceContract {


    CategoryResponseDto createCategory(
            CreateCategoryRequestDto request
    );


    CategoryResponseDto getCategory(
            UUID id
    );


    List<CategoryResponseDto> getCategories();


    CategoryResponseDto updateCategory(
            UUID id,
            CreateCategoryRequestDto request
    );


    void deactivateCategory(
            UUID id
    );

    void activateCategory(UUID id);
}