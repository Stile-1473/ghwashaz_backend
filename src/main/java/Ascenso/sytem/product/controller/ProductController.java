package Ascenso.sytem.product.controller;

import Ascenso.sytem.common.response.ApiResponse;
import Ascenso.sytem.product.dto.CreateProductRequestDto;
import Ascenso.sytem.product.dto.ProductResponseDto;
import Ascenso.sytem.product.dto.UpdateProductRequestDto;
import Ascenso.sytem.product.service.ProductServiceContract;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductServiceContract productService;

    @PostMapping
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).PRODUCT_CREATE)")
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(
            @Valid @RequestBody CreateProductRequestDto request
    ) {
        return ResponseEntity.ok(
                ApiResponse.<ProductResponseDto>builder()
                        .success(true)
                        .message("Product created successfully")
                        .data(productService.createProduct(request))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).PRODUCT_VIEW)")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProduct(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                ApiResponse.<ProductResponseDto>builder()
                        .success(true)
                        .message("Product fetched successfully")
                        .data(productService.getProduct(id))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).PRODUCT_VIEW)")
    public ResponseEntity<ApiResponse<?>> getProducts(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "categoryId", required = false) UUID categoryId,
            @RequestParam(name = "active", required = false) Boolean active,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Products fetched successfully")
                        .data(productService.getProducts(search, categoryId, active, pageable))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).PRODUCT_UPDATE)")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProduct(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateProductRequestDto request
    ) {
        return ResponseEntity.ok(
                ApiResponse.<ProductResponseDto>builder()
                        .success(true)
                        .message("Product updated successfully")
                        .data(productService.updateProduct(id, request))
                        .build()
        );
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).PRODUCT_UPDATE)")
    public ResponseEntity<ApiResponse<Void>> deactivateProduct(
            @PathVariable UUID id
    ) {
        productService.deactivateProduct(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Product disabled successfully")
                        .data(null)
                        .build()
        );
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).PRODUCT_UPDATE)")
    public ResponseEntity<ApiResponse<Void>> activateProduct(
            @PathVariable UUID id
    ) {
        productService.activateProduct(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Product enabled successfully")
                        .data(null)
                        .build()
        );
    }
}

