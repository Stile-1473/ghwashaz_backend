package Ascenso.sytem.product.validator;


import Ascenso.sytem.common.exception.BadRequestException;
import Ascenso.sytem.common.exception.ResourceNotFoundException;
import Ascenso.sytem.product.entity.Category;
import Ascenso.sytem.product.entity.Product;
import Ascenso.sytem.product.repository.CategoryRepository;
import Ascenso.sytem.product.repository.ProductRepository;
import Ascenso.sytem.product.validator.CategoryValidator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductValidator {

    private final ProductRepository productRepository;

    private final CategoryValidator categoryValidator;


    public Product getValidatedProduct(UUID id){

        return productRepository.findById(id)

                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found"
                        ));
    }

    public void validateUniqueName(String name){

        if(productRepository.existsByNameIgnoreCase(name)){

            throw new BadRequestException(
                    "Product name already exists"
            );

        }

    }

    public void validateBarcode(String barcode){

        if(barcode != null &&
                !barcode.isBlank() &&
                productRepository.existsByBarcode(barcode)){

            throw new BadRequestException(
                    "Barcode already exists"
            );

        }

    }

    public Category getValidatedCategory(UUID id) {
        return categoryValidator.getValidatedCategory(id);
    }

    public void validatePrices(
            BigDecimal cost,
            BigDecimal selling
    ){


        if(selling.compareTo(cost) < 0){

            throw new BadRequestException(
                    "Selling price cannot be less than cost price"
            );

        }

    }

}
