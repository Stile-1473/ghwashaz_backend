package Ascenso.sytem.inventory.mapper;

import Ascenso.sytem.common.mapper.MapperConfiguration;
import Ascenso.sytem.inventory.dto.InventoryMovementResponseDto;
import Ascenso.sytem.inventory.dto.InventoryResponseDto;
import Ascenso.sytem.inventory.entity.Inventory;
import Ascenso.sytem.inventory.entity.InventoryMovement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import Ascenso.sytem.user.entity.User;




@Mapper(componentModel = "spring", config = MapperConfiguration.class)
public interface InventoryMapper {


    @Mapping(source = "id",target = "inventoryId")
    @Mapping(source="product.id",target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.sku", target = "sku")
    @Mapping(source = "product.category.name", target = "category")
    @Mapping(source = "product.minimumStockLevel", target = "minimumStockLevel")
    @Mapping(target = "lowStock",
    expression = "java(inventory.getQuantityOnHand() <= inventory.getProduct().getMinimumStockLevel())"
    )
    @Mapping(source = "updatedAt",target = "lastUpdated")
    InventoryResponseDto toResponse(Inventory inventory);


    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "performedBy", target = "performedBy")
    @Mapping(source = "updatedAt", target = "createdAt")


    InventoryMovementResponseDto toMovementResponse(
            InventoryMovement movement
    );

   //mapping a user
    default String map(User user){
        if (user == null) {
            return null;
        }
        if (user.getFullName() != null && !user.getFullName().isBlank()) {
            return user.getFullName();
        }
        return user.getPhoneNumber();
    }






}

