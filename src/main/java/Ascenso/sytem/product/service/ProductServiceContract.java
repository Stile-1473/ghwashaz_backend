package Ascenso.sytem.product.service;

import Ascenso.sytem.product.dto.ChangePriceRequestDto;
import Ascenso.sytem.product.dto.CreateProductRequestDto;
import Ascenso.sytem.product.dto.ProductResponseDto;
import Ascenso.sytem.product.dto.UpdateProductRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductServiceContract {
    ProductResponseDto createProduct(CreateProductRequestDto requestDto);

    ProductResponseDto getProduct(UUID id);

    Page<ProductResponseDto> getProducts(
      String search,
      UUID categoryId,
      Boolean lowStock,
      Boolean active,
      Pageable pageable

    );

    ProductResponseDto updateProduct(UUID id, UpdateProductRequestDto requestDto);

    ProductResponseDto changePrice(UUID id, ChangePriceRequestDto requestDto);

    void deactivateProduct(UUID id);

    void  activateProduct(UUID id);



}
