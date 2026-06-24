package Ascenso.sytem.product.service;

import Ascenso.sytem.common.response.PageResponse;
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

    PageResponse<ProductResponseDto> getProducts(
      String search,
      UUID categoryId,
      Boolean active,
      Pageable pageable

    );

    ProductResponseDto updateProduct(UUID id, UpdateProductRequestDto requestDto);

    void deactivateProduct(UUID id);

    void  activateProduct(UUID id);



}
