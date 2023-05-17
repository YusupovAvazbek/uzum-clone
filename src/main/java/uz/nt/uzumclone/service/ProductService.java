package uz.nt.uzumclone.service;

import org.springframework.data.domain.Page;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;

import java.util.List;

public interface ProductService {
    public ResponseDto<ProductDto> addProduct(ProductDto productDto);
    public ResponseDto<List<ProductDto>> getExpensiveProducts();
    public ResponseDto<ProductDto> updateProduct(ProductDto productDto);
    public ResponseDto<Page<ProductDto>> getAllProducts(Integer page, Integer size);
    public ResponseDto<ProductDto> getProductById(Integer id);
}
