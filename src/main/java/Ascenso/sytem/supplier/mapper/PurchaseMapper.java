package Ascenso.sytem.supplier.mapper;

import Ascenso.sytem.common.mapper.MapperConfiguration;
import Ascenso.sytem.supplier.dto.CreatePurchaseRequestDto;
import Ascenso.sytem.supplier.dto.PurchaseItemResponseDto;
import Ascenso.sytem.supplier.dto.PurchaseResponseDto;
import Ascenso.sytem.supplier.entity.Purchase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", config = MapperConfiguration.class)
public interface PurchaseMapper {


    @Mapping(target = "purchaseNumber", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "receivedBy", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "receiptImageUrl", ignore = true)
    Purchase toEntity(CreatePurchaseRequestDto dto);

    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.companyName", target = "supplierName")
    @Mapping(source = "items", target = "items")
    PurchaseResponseDto toResponse(Purchase purchase);


    // Element mapping for Purchase -> PurchaseItemResponseDto
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.sku", target = "sku")
    PurchaseItemResponseDto map(Ascenso.sytem.supplier.entity.PurchaseItem item);

}
