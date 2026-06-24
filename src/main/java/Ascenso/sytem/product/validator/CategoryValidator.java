package Ascenso.sytem.product.validator;

import Ascenso.sytem.common.exception.BadRequestException;
import Ascenso.sytem.common.exception.ResourceNotFoundException;
import Ascenso.sytem.product.entity.Category;
import Ascenso.sytem.product.repository.CategoryRepository;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CategoryValidator {

    private final CategoryRepository categoryRepository;

    public void validateUniqueName(String name) {
        if (categoryRepository.existsByNameIgnoreCase(name)) {

            throw new BadRequestException(
                    "Category name already exists"
            );

        }
    }

    public Category getValidatedCategory(UUID id) {

        return categoryRepository.findById(id)

                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Category not found"

                        ));

    }


}
