package Ascenso.sytem.product.controller;

import Ascenso.sytem.common.response.ApiResponse;
import Ascenso.sytem.product.dto.CategoryResponseDto;
import Ascenso.sytem.product.dto.CreateCategoryRequestDto;
import Ascenso.sytem.product.dto.UpdateCategoryRequestDto;
import Ascenso.sytem.product.service.CategoryServiceContract;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryServiceContract categoryService;

    @PostMapping
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).CATEGORY_MANAGE)")
    public ResponseEntity<ApiResponse<CategoryResponseDto>> createCategory(
            @Valid @RequestBody CreateCategoryRequestDto request
    ) {
        return ResponseEntity.ok(
                ApiResponse.<CategoryResponseDto>builder()
                        .success(true)
                        .message("Category created successfully")
                        .data(categoryService.createCategory(request))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).CATEGORY_MANAGE)")
    public ResponseEntity<ApiResponse<CategoryResponseDto>> getCategory(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                ApiResponse.<CategoryResponseDto>builder()
                        .success(true)
                        .message("Category fetched successfully")
                        .data(categoryService.getCategory(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).CATEGORY_MANAGE)")
    public ResponseEntity<ApiResponse<CategoryResponseDto>> updateCategory(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCategoryRequestDto request
    ) {
        return ResponseEntity.ok(
                ApiResponse.<CategoryResponseDto>builder()
                        .success(true)
                        .message("Category updated successfully")
                        .data(categoryService.updateCategory(id, request))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).CATEGORY_MANAGE)")
    public ResponseEntity<ApiResponse<?>> getCategories(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "active", required = false) Boolean active,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Categories fetched successfully")
                        .data(categoryService.getCategories(search, active, pageable))
                        .build()
        );
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).CATEGORY_MANAGE)")
    public ResponseEntity<ApiResponse<Void>> deactivateCategory(
            @PathVariable UUID id
    ) {
        categoryService.deactivateCategory(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Category disabled successfully")
                        .data(null)
                        .build()
        );
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).CATEGORY_MANAGE)")
    public ResponseEntity<ApiResponse<Void>> activateCategory(
            @PathVariable UUID id
    ) {
        categoryService.activateCategory(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Category enabled successfully")
                        .data(null)
                        .build()
        );
    }
}

