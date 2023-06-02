package uz.nt.uzumclone.service.Impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.nt.uzumclone.dto.BrandDto;
import uz.nt.uzumclone.dto.CategoryDto;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.model.Brand;
import uz.nt.uzumclone.model.Product;
import uz.nt.uzumclone.repository.CategoryRepository;
import uz.nt.uzumclone.repository.ProductRepository;
import uz.nt.uzumclone.repository.ProductRepositoryImpl;
import uz.nt.uzumclone.service.CategoryService;
import uz.nt.uzumclone.service.mapper.BrandMapper;
import uz.nt.uzumclone.service.mapper.CategoryMapper;
import uz.nt.uzumclone.service.mapper.ProductMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static uz.nt.uzumclone.additional.AppStatusCodes.*;
import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final BrandMapper brandMapper;

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

    @Override
    public ResponseDto<Page<ProductDto>> getWithSort(Integer id, String sorting, String ordering, Integer currentPage) {
        Page<Product> sort = productRepository.getWithSort(id, sorting, ordering, currentPage);
        if(sort.isEmpty()){
            return ResponseDto.<Page<ProductDto>>builder()
                    .build();
        }
        return ResponseDto.<Page<ProductDto>>builder()
                .data(sort.map(productMapper::toDto))
                .code(OK_CODE)
                .message(OK)
                .build();
    }

    public ResponseDto<Set<BrandDto>> brandByCategory(Integer categoryId){
        ResponseDto<Page<ProductDto>> pageResponseDto = getWithSort(categoryId,null,null,0);
        Page<ProductDto> data = pageResponseDto.getData();
        if(!data.isEmpty()) {

            Set<BrandDto> collect = data.getContent()
                    .stream()
                    .map(p -> p.getBrand())
                    .collect(Collectors.toSet());
            return ResponseDto.<Set<BrandDto>>builder()
                    .data(collect)
                    .message(OK)
                    .code(OK_CODE)
                    .success(true)
                    .build();
        }
        return ResponseDto.<Set<BrandDto>>builder()
                .message(EMPTY_STRING)
                .build();
    }

    public ResponseDto<Page<ProductDto>> getByBrand(Integer id, List<String> filter, Integer currentPage) {
        Page<Product> productByBrand = productRepository.getProductByBrand(id, filter, currentPage);

        return ResponseDto.<Page<ProductDto>>builder()
                .data(productByBrand.map(product -> productMapper.toDto(product)))
                .build();
    }
}
