package Ascenso.sytem.sale.refund.controller;

import Ascenso.sytem.common.constants.Permissions;
import Ascenso.sytem.common.response.ApiResponse;
import Ascenso.sytem.sale.refund.dto.CreateReturnRequestDto;
import Ascenso.sytem.sale.refund.dto.ReturnResponseDto;
import Ascenso.sytem.sale.refund.service.ReturnServiceContract;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/returns")
@RequiredArgsConstructor
public class ReturnController {

    private final ReturnServiceContract returnService;

    @PostMapping
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).SALE_REFUND)")
    public ResponseEntity<ApiResponse<ReturnResponseDto>> createReturn(
            @RequestBody CreateReturnRequestDto dto) {
        ReturnResponseDto response = returnService.createReturn(dto);
        return ResponseEntity.ok(ApiResponse.<ReturnResponseDto>builder()
                .success(true)
                .message("Return created successfully")
                .data(response)
                .build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(Ascenso.sytem.common.constants.Permissions).SALE_REFUND)")
    public ResponseEntity<ApiResponse<ReturnResponseDto>> getReturn(
            @PathVariable UUID id) {
        ReturnResponseDto response = returnService.getReturn(id);
        return ResponseEntity.ok(ApiResponse.<ReturnResponseDto>builder()
                .success(true)
                .data(response)
                .build());
    }
}
