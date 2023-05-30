package uz.nt.uzumclone.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import uz.nt.uzumclone.dto.CategoryDto;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;

public interface CategoryService {
    ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto);
    ResponseDto<Page<ProductDto>> get(Integer id,Integer currentPage);
    ResponseDto<Page<ProductDto>> sort(Integer id, String sorting, String ordering, Integer currentPage);
}
