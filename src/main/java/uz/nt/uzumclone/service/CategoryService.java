package uz.nt.uzumclone.service;

import uz.nt.uzumclone.dto.CategoryDto;
import uz.nt.uzumclone.dto.ResponseDto;

public interface CategoryService {
    public ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto);
}
