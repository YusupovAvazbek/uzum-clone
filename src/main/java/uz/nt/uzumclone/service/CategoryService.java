package uz.nt.uzumclone.service;

import uz.nt.uzumclone.dto.CategoryDto;
import uz.nt.uzumclone.dto.CommonDto;
import uz.nt.uzumclone.dto.ResponseDto;

import java.util.List;
public interface CategoryService {
    ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto);
    ResponseDto<CommonDto> getWithSort(Integer id, List<String> filter, String sorting, String ordering, Integer currentPage);
}
