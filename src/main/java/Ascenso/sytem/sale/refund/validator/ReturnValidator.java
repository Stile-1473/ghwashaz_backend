package Ascenso.sytem.sale.refund.validator;

import Ascenso.sytem.common.exception.BadRequestException;
import Ascenso.sytem.common.exception.ResourceNotFoundException;
import Ascenso.sytem.sale.entity.Sale;
import Ascenso.sytem.sale.repository.SaleRepository;
import Ascenso.sytem.sale.refund.entity.SaleReturn;
import Ascenso.sytem.sale.refund.repository.ReturnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReturnValidator {

    private final SaleRepository saleRepository;
    private final ReturnRepository returnRepository;

    public Sale getValidatedSale(UUID saleId) {
        return saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));
    }

    public void validateReturnAmount(Sale sale, BigDecimal amount) {
        if (amount.compareTo(sale.getTotal()) > 0) {
            throw new BadRequestException("Return amount exceeds original sale total");
        }
    }

    public void validateSaleCanBeReturned(Sale sale) {
        if (sale.getStatus() == Ascenso.sytem.common.enums.SaleStatus.CANCELLED) {
            throw new BadRequestException("Cannot return a cancelled sale");
        }
    }

}
