package uz.nt.uzumclone.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.nt.uzumclone.dto.BrandDto;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.model.Brand;
import uz.nt.uzumclone.model.Product;
import uz.nt.uzumclone.repository.ProductRepository;
import uz.nt.uzumclone.service.BrandServices;
import uz.nt.uzumclone.service.ProductService;
import uz.nt.uzumclone.service.mapper.CategoryMapper;
import uz.nt.uzumclone.service.mapper.ProductMapper;

import java.util.Optional;

import static uz.nt.uzumclone.additional.AppStatusCodes.*;
import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;
    private final BrandServices brandServices;

    @Override
    public ResponseDto<ProductDto> addProduct(ProductDto productDto) {
        Brand brand = brandServices.addBrand(productDto.getBrand());
        Product product = productMapper.toEntity(productDto);
        product.setBrand(brand);
        try {
            productRepository.save(product);

            return ResponseDto.<ProductDto>builder()
                    .success(true)
                    .data(productMapper.toDto(product))
                    .message("OK")
                    .build();
        } catch (Exception e) {
            return ResponseDto.<ProductDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }

    }

    @Override
    public ResponseDto<ProductDto> updateProduct(ProductDto productDto) {
        if (productDto.getId() == null) {
            return ResponseDto.<ProductDto>builder()
                    .message("Product ID is null")
                    .code(VALIDATION_ERROR_CODE)
                    .build();
        }

        Optional<Product> optional = productRepository.findById(productDto.getId());

        if (optional.isEmpty()) {
            return ResponseDto.<ProductDto>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .build();
        }

        Product product = optional.get();

        if (productDto.getName() != null) {
            product.setName(productDto.getName());
        }
        if (productDto.getPrice() != null) {
            product.setPrice(productDto.getPrice());
        }
        if (productDto.getAmount() != null && productDto.getAmount() > 0) {
            product.setIsAvailable(true);
            product.setAmount(productDto.getAmount());
        }
        if (productDto.getDescription() != null) {
            product.setDescription(productDto.getDescription());
        }
        if (productDto.getCategory() != null) {
            product.setCategory(categoryMapper.toEntity(productDto.getCategory()));
        }

        try {
            productRepository.save(product);

            return ResponseDto.<ProductDto>builder()
                    .message(OK)
                    .data(productMapper.toDto(product))
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<ProductDto>builder()
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .code(DATABASE_ERROR_CODE)
                    .build();
        }
    }

    @Override
    public ResponseDto<Page<ProductDto>> getAllProducts(Integer page, Integer size) {
        Long count = productRepository.count();

        PageRequest pageRequest = PageRequest.of(
                (count / size) <= page ?
                        (count % size == 0 ? (int) (count / size) - 1
                                : (int) (count / size))
                        : page,
                size);

        Page<ProductDto> products = productRepository.findAll(pageRequest)
                .map(productMapper::toDto);

        return ResponseDto.<Page<ProductDto>>builder()
                .message(OK)
                .success(true)
                .data(products)
                .build();
    }

    @Override
    public ResponseDto<ProductDto> getProductById(Integer id) {
        return productRepository.findById(id)
                .map(products -> ResponseDto.<ProductDto>builder()
                        .data(productMapper.toDto(products))
                        .success(true)
                        .code(OK_CODE)
                        .message(OK)
                        .build())
                .orElse(ResponseDto.<ProductDto>builder()
                        .message(NOT_FOUND)
                        .code(NOT_FOUND_ERROR_CODE)
                        .build()
                );
    }
}
