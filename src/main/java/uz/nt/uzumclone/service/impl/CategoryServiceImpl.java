package uz.nt.uzumclone.service.Impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.nt.uzumclone.dto.CategoryDto;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.model.Product;
import uz.nt.uzumclone.repository.CategoryRepository;
import uz.nt.uzumclone.repository.ProductRepository;
import uz.nt.uzumclone.repository.ProductRepositoryImpl;
import uz.nt.uzumclone.service.CategoryService;
import uz.nt.uzumclone.service.mapper.CategoryMapper;
import uz.nt.uzumclone.service.mapper.ProductMapper;

import java.util.Map;

import static uz.nt.uzumclone.additional.AppStatusCodes.*;
import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto) {
        try {
            return ResponseDto.<CategoryDto>builder()
                    .data(categoryMapper.toDto(
                            categoryRepository.save(
                                    categoryMapper.toEntity(categoryDto)
                            )
                    ))
                    .message(OK)
                    .success(true)
                    .build();
        }catch (Exception e){
            return ResponseDto.<CategoryDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + " : " + e.getMessage())
                    .data(categoryDto)
                    .build();
        }
    }

    public ResponseDto<Page<ProductDto>> get(Integer id,Integer currentPage) {
        Page<Product> products = productRepository.getByCategory(id,currentPage);
        if(products.isEmpty()){
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

    public ResponseDto<Page<ProductDto>> sort(String sorting, Integer page, Integer size) {
        productRepository.sort(sorting);
        return null;
    }
}
