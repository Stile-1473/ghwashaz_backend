package Ascenso.sytem.supplier.controller;

import Ascenso.sytem.common.constants.Permissions;
import Ascenso.sytem.common.response.ApiResponse;
import Ascenso.sytem.supplier.dto.CreateSupplierRequestDto;
import Ascenso.sytem.supplier.dto.SupplierResponseDto;
import Ascenso.sytem.supplier.dto.UpdateSupplierRequestDto;
import Ascenso.sytem.supplier.service.SupplierServiceContract;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/suppliers")
public class SupplierController {

    private final SupplierServiceContract supplierService;

    @PostMapping
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions)." + Permissions.PURCHASE_CREATE + ")")
    public ResponseEntity<ApiResponse<SupplierResponseDto>> createSupplier(
            @Valid @RequestBody CreateSupplierRequestDto request
    ) {
        return ResponseEntity.ok(
                ApiResponse.<SupplierResponseDto>builder()
                        .success(true)
                        .message("Supplier created successfully")
                        .data(supplierService.createSupplier(request))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions)." + Permissions.PURCHASE_APPROVE + ")")
    public ResponseEntity<ApiResponse<SupplierResponseDto>> getSupplier(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                ApiResponse.<SupplierResponseDto>builder()
                        .success(true)
                        .message("Supplier fetched successfully")
                        .data(supplierService.getSupplier(id))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions)." + Permissions.PURCHASE_APPROVE + ")")
    public ResponseEntity<ApiResponse<?>> getSuppliers(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "active", required = false) Boolean active,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Suppliers fetched successfully")
                        .data(supplierService.getSuppliers(search, active, pageable))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions)." + Permissions.PURCHASE_APPROVE + ")")
    public ResponseEntity<ApiResponse<SupplierResponseDto>> updateSupplier(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateSupplierRequestDto request
    ) {
        return ResponseEntity.ok(
                ApiResponse.<SupplierResponseDto>builder()
                        .success(true)
                        .message("Supplier updated successfully")
                        .data(supplierService.updateSupplier(id, request))
                        .build()
        );
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions)." + Permissions.PURCHASE_APPROVE + ")")
    public ResponseEntity<ApiResponse<Void>> activateSupplier(
            @PathVariable UUID id
    ) {
        supplierService.activateSupplier(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Supplier enabled successfully")
                        .data(null)
                        .build()
        );
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions)." + Permissions.PURCHASE_APPROVE + ")")
    public ResponseEntity<ApiResponse<Void>> deactivateSupplier(
            @PathVariable UUID id
    ) {
        supplierService.deactivateSupplier(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Supplier disabled successfully")
                        .data(null)
                        .build()
        );
    }
}

