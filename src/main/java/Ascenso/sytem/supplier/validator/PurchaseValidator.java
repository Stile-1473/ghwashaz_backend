package Ascenso.sytem.supplier.validator;

import Ascenso.sytem.common.enums.PurchaseStatus;
import Ascenso.sytem.common.exception.BadRequestException;
import Ascenso.sytem.common.exception.ResourceNotFoundException;
import Ascenso.sytem.supplier.entity.Purchase;
import Ascenso.sytem.supplier.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PurchaseValidator {

    private final PurchaseRepository purchaseRepository;

    public Purchase getValidatedPurchase(UUID id){

        return purchaseRepository.findById(id)

                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Purchase not found"
                        ));

    }

    public void validateDraft(Purchase purchase){

        if(purchase.getStatus() != PurchaseStatus.DRAFT){

            throw new BadRequestException(
                    "Only draft purchases can be modified"
            );

        }

    }

}