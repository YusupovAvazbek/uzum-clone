package uz.nt.uzumclone.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumclone.dto.CategoryDto;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.service.Impl.CategoryServiceImpl;

import java.util.Map;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryResources {

    private final CategoryServiceImpl categoryService;
    @GetMapping("/{id}")
    public ResponseDto<Page<ProductDto>> get(@PathVariable Integer id,@RequestParam(defaultValue = "1") Integer currentPage){
        return categoryService.get(id, currentPage);
    }
    @PostMapping
    public ResponseDto<CategoryDto> addCategory(@RequestBody @Valid CategoryDto categoryDto){
        return categoryService.addCategory(categoryDto);
    }

    @GetMapping("/sort")
    public ResponseDto<Page<ProductDto>> sort(@RequestParam String sorting,@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10") Integer size){
        return  categoryService.sort(sorting,page,size);
    }
}
