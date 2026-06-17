package Ascenso.sytem.supplier.service;

import Ascenso.sytem.common.enums.PurchaseOrderStatus;
import Ascenso.sytem.supplier.dto.CreatePurchaseRequestDto;
import Ascenso.sytem.supplier.dto.PurchaseResponseDto;
import Ascenso.sytem.supplier.dto.ReceivePurchaseRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.UUID;

public interface PurchaseServiceContract {

    PurchaseResponseDto createPurchase(CreatePurchaseRequestDto requestDto);

    PurchaseResponseDto getPurchase(UUID id);

    Page<PurchaseResponseDto> getPurchases(
            PurchaseOrderStatus status,
            UUID supplierId,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );

    PurchaseResponseDto receivePurchase(
            UUID id,
            ReceivePurchaseRequestDto request
    );


    PurchaseResponseDto confirmPurchase(
            UUID id
    );


    void cancelPurchase(
            UUID id
    );
}
