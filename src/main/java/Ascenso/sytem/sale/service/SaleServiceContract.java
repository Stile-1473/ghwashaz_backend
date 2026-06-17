package Ascenso.sytem.sale.service;

import Ascenso.sytem.common.enums.PaymentMethod;
import Ascenso.sytem.common.enums.SaleStatus;
import Ascenso.sytem.sale.dto.CreateSaleRequestDto;
import Ascenso.sytem.sale.dto.ReceiptResponseDto;
import Ascenso.sytem.sale.dto.SaleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.UUID;

public interface SaleServiceContract {

    SaleResponseDto createSale(CreateSaleRequestDto saleRequestDto);

    SaleResponseDto getSale(UUID id);

    Page<SaleResponseDto> getSales(
            LocalDate startDate,
            LocalDate endDate,
            UUID cashierId,
            UUID customerId,
            PaymentMethod paymentMethod,
            SaleStatus saleStatus,
            Pageable pageable
    );

    void voidSale(UUID id);

    ReceiptResponseDto getReceipt(UUID id);

}
