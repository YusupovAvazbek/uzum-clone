package uz.nt.uzumclone.service;

import org.springframework.data.domain.Page;
import uz.nt.uzumclone.dto.CategoryDto;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.projections.ProductProjection;
import java.util.List;
public interface CategoryService {
    ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto);
    ResponseDto<Page<ProductDto>> getWithSort(Integer id, List<String> filter,String sorting, String ordering, Integer currentPage);
}
