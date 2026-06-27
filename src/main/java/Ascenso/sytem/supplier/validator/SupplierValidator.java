package Ascenso.sytem.supplier.validator;

import Ascenso.sytem.common.exception.BadRequestException;
import Ascenso.sytem.common.exception.ResourceNotFoundException;
import Ascenso.sytem.supplier.entity.Supplier;
import Ascenso.sytem.supplier.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SupplierValidator {


    private final SupplierRepository supplierRepository;

    public Supplier getValidatedSupplier(UUID id) {

        return supplierRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Supplier not found"
                ));

    }


    public void validateCompanyName(String companyName){

        if(supplierRepository.existsByCompanyNameIgnoreCase(companyName)){

            throw new BadRequestException(
                    "Supplier company already exists"
            );
        }
    }


    public void validatePhoneNumber(String phoneNumber){

        if(supplierRepository.existsByPhoneNumber(phoneNumber)){

            throw new BadRequestException(
                    "Phone number already exists"
            );

        }

    }



}
