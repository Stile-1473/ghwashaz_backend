package Ascenso.sytem.supplier.service;

import Ascenso.sytem.common.response.PageResponse;
import Ascenso.sytem.supplier.dto.CreatePurchaseRequestDto;
import Ascenso.sytem.supplier.dto.PurchaseResponseDto;
import Ascenso.sytem.supplier.dto.PurchaseWithItemsResponseDto;
import Ascenso.sytem.supplier.dto.ReceivePurchaseRequestDto;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PurchaseServiceContract {

    PurchaseResponseDto createPurchase(CreatePurchaseRequestDto dto);

    PurchaseResponseDto getPurchase(UUID id);

    PurchaseWithItemsResponseDto getPurchaseWithItems(UUID id);

    PageResponse<PurchaseResponseDto> getPurchases(String search, UUID supplierId, String status, Pageable pageable);

    PurchaseResponseDto receivePurchase(UUID id, ReceivePurchaseRequestDto dto);

    PurchaseResponseDto approveShortDelivery(UUID id);

    void cancelPurchase(UUID id);
}
