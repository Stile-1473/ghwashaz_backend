package Ascenso.sytem.sale.validator;

import Ascenso.sytem.common.enums.SaleStatus;
import Ascenso.sytem.common.exception.BadRequestException;
import Ascenso.sytem.common.exception.ResourceNotFoundException;
import Ascenso.sytem.sale.entity.Sale;
import Ascenso.sytem.sale.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SaleValidator {

    private final SaleRepository saleRepository;

    public Sale getValidatedSale(UUID id) {
        Sale sale = saleRepository.findByIdWithItems(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));

        saleRepository.findByIdWithPayments(id).ifPresent(s -> {
            sale.getPayments().clear();
            sale.getPayments().addAll(s.getPayments());
        });
        
        return sale;
    }

    public void validateDraft(Sale sale) {
        if (sale.getStatus() != SaleStatus.DRAFT) {
            throw new BadRequestException("Sale is not in draft status");
        }
    }

    public void validateCanVoid(Sale sale) {
        if (sale.getStatus() == SaleStatus.CANCELLED ||
            sale.getStatus() == SaleStatus.VOIDED) {
            throw new BadRequestException("Sale is already cancelled/voided");
        }
    }
}
