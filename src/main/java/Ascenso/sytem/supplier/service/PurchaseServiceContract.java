package Ascenso.sytem.supplier.service;

import Ascenso.sytem.common.response.PageResponse;
import Ascenso.sytem.supplier.dto.PurchaseResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PurchaseServiceContract {

    PurchaseResponseDto createPurchase(
            Ascenso.sytem.purchase.dto.CreatePurchaseRequestDto dto
    );

    PurchaseResponseDto getPurchase(
            UUID id
    );

    PageResponse<PurchaseResponseDto> getPurchases(
            String search,
            UUID supplierId,
            String status,
            Pageable pageable
    );

    PurchaseResponseDto receivePurchase(
            UUID id
    );

    void cancelPurchase(
            UUID id
    );

}