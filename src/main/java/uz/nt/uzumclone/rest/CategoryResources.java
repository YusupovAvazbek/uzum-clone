package uz.nt.uzumclone.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumclone.dto.CategoryDto;
import uz.nt.uzumclone.dto.*;
import uz.nt.uzumclone.service.Impl.CategoryServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryResources {

    private final CategoryServiceImpl categoryService;
    @GetMapping("/{id}")
    public ResponseDto<CommonDto> get(@PathVariable Integer id,
                                      @RequestParam(required = false) String sorting,
                                      @RequestParam(required = false) String ordering,
                                      @RequestParam(required = false) List<String> filter,
                                      @RequestParam(required = false,defaultValue = "10") Integer size,
                                      @RequestParam(required = false, defaultValue = "0") Integer currentPage){
        return categoryService.getWithSort(id, filter,sorting,ordering,currentPage);
    }
    @PostMapping
    public ResponseDto<CategoryDto> addCategory(@RequestBody @Valid CategoryDto categoryDto){
        return categoryService.addCategory(categoryDto);
    }
    @GetMapping("/{id}/category")
    public ResponseDto<List<CategoryDto>> category(@PathVariable Integer id){
        return categoryService.category(id);
    }
}
