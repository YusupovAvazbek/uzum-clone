package uz.nt.uzumclone.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.model.Product;
import uz.nt.uzumclone.repository.ProductRepository;
import uz.nt.uzumclone.service.ProductService;
import uz.nt.uzumclone.service.mapper.ProductMapper;

import java.util.List;

import static uz.nt.uzumclone.additional.AppStatusCodes.*;
import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    @Override
    public ResponseDto<ProductDto> addProduct(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        try{
            productRepository.save(product);

            return ResponseDto.<ProductDto>builder()
                    .success(true)
                    .data(productMapper.toDto(product))
                    .message("OK")
                    .build();
        } catch (Exception e){
            return ResponseDto.<ProductDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
        
    }

    @Override
    public ResponseDto<List<ProductDto>> getExpensiveProducts() {
        return null;
    }

    @Override
    public ResponseDto<ProductDto> updateProduct(ProductDto productDto) {
        return null;
    }

    @Override
    public ResponseDto<Page<ProductDto>> getAllProducts(Integer page, Integer size) {
        return null;
    }

    @Override
    public ResponseDto<ProductDto> getProductById(Integer id) {
        return null;
    }
}
