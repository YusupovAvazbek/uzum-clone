package uz.nt.uzumclone.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import uz.nt.uzumclone.dto.CategoryDto;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.model.Product;

public interface CategoryService {
    ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto);
    ResponseDto<Page<ProductDto>> getWithSort(Integer id, String sorting, String ordering, Integer currentPage);
}
