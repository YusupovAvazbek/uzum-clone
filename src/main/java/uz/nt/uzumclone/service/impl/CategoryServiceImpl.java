package uz.nt.uzumclone.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumclone.dto.CategoryDto;
import uz.nt.uzumclone.dto.*;
import uz.nt.uzumclone.model.Category;
import uz.nt.uzumclone.repository.CategoryRepository;
import uz.nt.uzumclone.repository.ProductRepository;
import uz.nt.uzumclone.service.CategoryService;
import uz.nt.uzumclone.service.mapper.BrandMapper;
import uz.nt.uzumclone.service.mapper.CategoryMapper;
import uz.nt.uzumclone.service.mapper.ProductMapper;

import java.util.List;

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
    public ResponseDto<CommonDto> getWithSort(Integer id, List<String> filter, String sorting, String ordering, Integer currentPage) {
        CommonDto sort = productRepository.getWithSort(id,filter, sorting, ordering, currentPage);
        if(sort == null){
            return ResponseDto.<CommonDto>builder()
                    .message(EMPTY_STRING)
                    .code(OK_CODE)
                    .success(true)
                    .build();
        }
        return ResponseDto.<CommonDto>builder()
                .data(sort)
                .code(OK_CODE)
                .message(OK)
                .build();
    }


    public ResponseDto<List<CategoryDto>> category(Integer id) {
        List<Category> category = productRepository.getCategory(id);
        return ResponseDto.<List<CategoryDto>>builder()
                .data(category.stream().map(c->categoryMapper.toDto(c)).toList())
                .build();
    }
}
