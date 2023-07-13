package uz.nt.uzumclone.service.Impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import jakarta.validation.OverridesAttribute;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.nt.uzumclone.additional.AppStatusCodes;
import uz.nt.uzumclone.additional.AppStatusMessages;
import uz.nt.uzumclone.dto.*;
import uz.nt.uzumclone.model.*;
import uz.nt.uzumclone.projections.ProductProjection;
import uz.nt.uzumclone.repository.ProductDetailsRepository;
import uz.nt.uzumclone.repository.ProductRepository;
import uz.nt.uzumclone.repository.ProductRepositoryImpl;
import uz.nt.uzumclone.repository.ProductVariantRepository;
import uz.nt.uzumclone.service.BrandServices;
import uz.nt.uzumclone.service.ProductService;
import uz.nt.uzumclone.service.mapper.CategoryMapper;
import uz.nt.uzumclone.service.mapper.ProductMapper;
import uz.nt.uzumclone.service.mapper.ProductVariantMapper;
import uz.nt.uzumclone.service.mapper.VariantValueMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static uz.nt.uzumclone.additional.AppStatusCodes.*;
import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;
    private final BrandServiceImpl brandServices;
    private final ProductVariantMapper productVariantMapper;
    private final ProductVariantRepository productVariantRepository;
    private final VariantValueMapper variantValueMapper;
    private final ProductDetailsRepository productDetailsRepository;

    @Override
    public ResponseDto<ProductDto> addProduct(ProductDto productDto) {
        try {
            Brand brand = brandServices.addBrand(productDto.getBrand().getName());
            Product product = productMapper.toEntity(productDto);
            product.setBrand(brand);

            Product savedProduct = productRepository.save(product);

            List<ProductVariantDto> productVariants = productDto.getProductVariants();
            List<ProductVariant> savedVariants = new ArrayList<>();



            if (productVariants != null && !productVariants.isEmpty()) {
                for (ProductVariantDto variantDto : productVariants) {
                    ProductVariant variant = productVariantMapper.toEntity(variantDto);
                    variant.setProduct(savedProduct);
                    ProductVariant savedVariant = productVariantRepository.save(variant);
                    savedVariants.add(savedVariant);

                    List<VariantValueDto> variantValues = variantDto.getVariantValueIds();
                    if (variantValues != null && !variantValues.isEmpty()) {
                        for (VariantValueDto valueDto : variantValues) {
                            VariantValue value = variantValueMapper.toEntity(valueDto);

                            ProductDetails productDetails = new ProductDetails();
                            productDetails.setProductVariant(savedVariant);
                            productDetails.setVariantValue(value);

                            savedVariant.getProductDetails().add(productDetails);
                            productDetailsRepository.save(productDetails);
                        }
                    }
                }
            }

            Product updatedProduct = productRepository.save(savedProduct);

            return ResponseDto.<ProductDto>builder()
                    .success(true)
                    .data(productMapper.toDto(updatedProduct))
                    .message(OK)
                    .code(OK_CODE)
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
    @Transactional
    public ResponseDto<ProductDto> getProductById(Integer id) {
        Integer userId = 1;
        try {
            Optional<Product> byId = productRepository.findById(id);
            if (!byId.isEmpty()) {
                productRepository.insertViewedProduct(userId, id);
                return ResponseDto.<ProductDto>builder()
                        .data(productMapper.toDto(byId.get()))
                        .success(true)
                        .code(OK_CODE)
                        .message(OK)
                        .build();
            }
            return ResponseDto.<ProductDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_ERROR_CODE)
                    .build();

        } catch (Exception e) {
            return ResponseDto.<ProductDto>builder()
                    .message(DATABASE_ERROR)
                    .code(DATABASE_ERROR_CODE)
                    .build();
        }


    }

    @Override
    public ResponseDto<List<ProductProjection>> getProducts(Integer userId,Integer currentPage, Integer size) {
        try {
            List<ProductProjection> products = productRepository.getProducts(userId,currentPage,size);
            return ResponseDto.<List<ProductProjection>>builder()
                    .success(true)
                    .code(OK_CODE)
                    .message(OK)
                    .data(products)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<List<ProductProjection>>builder()
                    .success(false)
                    .code(OK_CODE)
                    .message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<List<ProductProjection>> getViewedProduct(Integer userId) {
        try {
            List<ProductProjection> productViewed = productRepository.getViewedProducts(userId);
            return ResponseDto.<List<ProductProjection>>builder()
                    .message(OK)
                    .code(OK_CODE)
                    .data(productViewed)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<List<ProductProjection>>builder()
                    .message(DATABASE_ERROR + ":" + e.getMessage())
                    .success(true)
                    .code(DATABASE_ERROR_CODE)
                    .build();
        }
    }

    public ResponseDto<Page<ProductDto>> universalSearch(String query, List<String> filter,String sorting, String ordering, Integer size, Integer currentPage) {
        Page<Product> products = productRepository.universalSearch(query,filter,sorting, ordering, size, currentPage);
        if(products.isEmpty()) {
            return ResponseDto.<Page<ProductDto>>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .success(false)
                    .message(NOT_FOUND)
                    .build();
        }
        return ResponseDto.<Page<ProductDto>>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(products.map(productMapper::toDto))
                .build();
    }
}
