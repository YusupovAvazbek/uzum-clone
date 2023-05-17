package uz.nt.uzumclone.service;

import uz.nt.uzumclone.dto.CategoryDto;

public interface CategoryService {
    public ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto);
}
