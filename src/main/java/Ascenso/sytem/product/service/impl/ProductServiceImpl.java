package Ascenso.sytem.product.service.impl;

import Ascenso.sytem.audit.service.AuditServiceContract;
import Ascenso.sytem.common.enums.AuditActionType;
import Ascenso.sytem.common.enums.AuditModule;

import Ascenso.sytem.common.mapper.PageMapper;
import Ascenso.sytem.common.response.PageResponse;
import Ascenso.sytem.inventory.service.InventoryServiceContract;
import Ascenso.sytem.product.dto.CreateProductRequestDto;
import Ascenso.sytem.product.dto.ProductResponseDto;
import Ascenso.sytem.product.dto.UpdateProductRequestDto;
import Ascenso.sytem.product.entity.Category;
import Ascenso.sytem.product.entity.Product;
import Ascenso.sytem.product.mapper.ProductMapper;

import Ascenso.sytem.product.repository.ProductRepository;
import Ascenso.sytem.product.service.ProductServiceContract;
import Ascenso.sytem.product.specification.ProductSpecification;
import Ascenso.sytem.product.util.ProductSkuGenerator;
import Ascenso.sytem.product.validator.ProductValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductServiceContract {

    private final ProductRepository productRepository;



    private final ProductMapper productMapper;

    private final ProductValidator productValidator;

    private final ProductSkuGenerator skuGenerator;

    private final AuditServiceContract auditService;

    private final InventoryServiceContract inventoryServiceContract;

    @Override
    public ProductResponseDto createProduct(CreateProductRequestDto requestDto) {

        productValidator.validateUniqueName(requestDto.getName());


        productValidator.validateBarcode(requestDto.getBarcode());

        productValidator.validatePrices(
                requestDto.getCostPrice(),
                requestDto.getSellingPrice()
        );

        Category category = productValidator.getValidatedCategory(requestDto.getCategoryId());


        Product product = productMapper.toEntity(requestDto);

        product.setCategory(category);

        product.setSku(
                skuGenerator.generate(product.getName())
        );

        product.setActive(true);

        Product saved =
                productRepository.save(product);

        inventoryServiceContract.createInventory(saved);


        try {
            auditService.log(
                    AuditModule.PRODUCT,
                    AuditActionType.CREATE,
                    saved.getId(),
                    "Created new product " + saved.getName()
            );
        } catch (Exception e) {
            log.warn(
                    "Audit log failed for product creation of {}, error={}",
                    saved.getName(),
                    e.getMessage()
            );
        } finally {
            return productMapper.toResponse(saved);
        }





    }

    @Override
    public ProductResponseDto getProduct(UUID id) {

        return productMapper.toResponse(

                productValidator.getValidatedProduct(id)

        );
    }

    @Override
    public PageResponse<ProductResponseDto> getProducts(String search, UUID categoryId, Boolean active, Pageable pageable) {

        Page<Product> page =
                productRepository.findAll(

                        ProductSpecification.search(

                                search,

                                categoryId,

                                active

                        ),

                        pageable

                );

        return PageMapper.from(

                page.map(productMapper::toResponse)

        );
    }

    @Override
    public ProductResponseDto updateProduct(UUID id, UpdateProductRequestDto requestDto) {
        Product product =
                productValidator.getValidatedProduct(id);

        if(!product.getName().equalsIgnoreCase(requestDto.getName())){

            productValidator.validateUniqueName(requestDto.getName());

        }
        if(requestDto.getBarcode() != null &&
                !requestDto.getBarcode().equals(product.getBarcode())){

            productValidator.validateBarcode(requestDto.getBarcode());

        }

        productValidator.validatePrices(
                requestDto.getCostPrice(),
                requestDto.getSellingPrice()
        );

        Category category = productValidator.getValidatedCategory(requestDto.getCategoryId());


        productMapper.updateEntity(requestDto, product);

        product.setCategory(category);

        Product saved =
                productRepository.save(product);


        try {
            auditService.log(
                    AuditModule.PRODUCT,
                    AuditActionType.UPDATE,
                    saved.getId(),
                    "Updated product " + saved.getName()
            );
        } catch (Exception e) {
            log.warn(
                    "Audit log failed for product update of {}, error={}",
                    saved.getName(),
                    e.getMessage()
            );
        } finally {
            return productMapper.toResponse(saved);
        }



    }

    @Override
    public void deactivateProduct(UUID id){

        Product product =
                productValidator.getValidatedProduct(id);

        product.setActive(false);

        productRepository.save(product);

        try {
            auditService.log(
                    AuditModule.PRODUCT,
                    AuditActionType.DISABLE,
                    product.getId(),
                    "Deactivated product " + product.getName()
            );
        } catch (Exception e) {
            log.warn(
                    "Audit log failed for deactivating product of {}, error={}",
                    product.getName(),
                    e.getMessage()
            );
        } finally {
            //
        }


    }

    @Override
    public void activateProduct(UUID id){

        Product product =
                productValidator.getValidatedProduct(id);

        product.setActive(true);

        productRepository.save(product);

        try {
            auditService.log(
                    AuditModule.PRODUCT,
                    AuditActionType.ENABLE,
                    product.getId(),
                    "Activated product " + product.getName()
            );
        } catch (Exception e) {
            log.warn(
                    "Audit log failed for activating product of {}, error={}",
                    product.getName(),
                    e.getMessage()
            );
        } finally {
            //
        }


    }
}
