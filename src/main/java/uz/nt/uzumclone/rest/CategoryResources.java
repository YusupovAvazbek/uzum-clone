package uz.nt.uzumclone.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumclone.dto.BrandDto;
import uz.nt.uzumclone.dto.CategoryDto;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.service.Impl.CategoryServiceImpl;

import java.util.Map;
import java.util.Set;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryResources {

    private final CategoryServiceImpl categoryService;
    @GetMapping("/{id}")
    public ResponseDto<Page<ProductDto>> get(@PathVariable Integer id,
                                             @RequestParam(defaultValue = "1") Integer currentPage){
        return categoryService.get(id, currentPage);
    }
    @PostMapping
    public ResponseDto<CategoryDto> addCategory(@RequestBody @Valid CategoryDto categoryDto){
        return categoryService.addCategory(categoryDto);
    }

    @GetMapping("/sort/{id}")
    public ResponseDto<Page<ProductDto>> sort(
            @PathVariable Integer id,
            @RequestParam String sorting,
            @RequestParam String ordering,
            @RequestParam Integer currentPage){
        return  categoryService.sort(id,sorting,ordering,currentPage);
    }
    @GetMapping("/{id}/brand")
    public ResponseDto<Page<ProductDto>> byBrand(@PathVariable Integer id,
                                                 @RequestParam List<String> filter) {
        return categoryService.getByBrand(id,filter);
    }
    @GetMapping("/{id}/brands")
    public ResponseDto<Set<BrandDto>> brands(@PathVariable Integer id){
        return categoryService.brandByCategory(id);
    }

}
