package Ascenso.sytem.inventory.validator;

import Ascenso.sytem.common.exception.BadRequestException;
import Ascenso.sytem.common.exception.ResourceNotFoundException;
import Ascenso.sytem.inventory.entity.Inventory;
import Ascenso.sytem.inventory.repository.InventoryRepository;
import Ascenso.sytem.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryValidator {
    private final InventoryRepository inventoryRepository;


    public Inventory getInventory(Product product){

        return inventoryRepository.findByProduct(product)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Inventory record not found"
                ));

    }

    public void validateStockAvailable(Inventory inventory,Integer quantity){

        if(inventory.getQuantityAvailable() < quantity){
            throw new BadRequestException("Insufficient stock available");
        }
    }
}
