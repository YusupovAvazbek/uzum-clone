package uz.nt.uzumclone.service;

import org.springframework.data.domain.Page;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.projections.ProductProjection;

import java.util.List;

public interface ProductService {
    ResponseDto<ProductDto> addProduct(ProductDto productDto);
    ResponseDto<ProductDto> updateProduct(ProductDto productDto);
    ResponseDto<Page<ProductDto>> getAllProducts(Integer page, Integer size);
    ResponseDto<ProductDto> getProductById(Integer id);

    ResponseDto<List<ProductProjection>> getProducts(Integer userId);
    ResponseDto<List<ProductProjection>> getProductsWithNativeQuery(Integer userId);

    ResponseDto<List<ProductProjection>> getViewedProduct(Integer userId);
}
