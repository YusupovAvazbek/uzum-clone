package uz.nt.uzumclone.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumclone.dto.BrandDto;
import uz.nt.uzumclone.dto.CategoryDto;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.service.Impl.CategoryServiceImpl;

import java.util.List;
import java.util.Set;

import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryResources {

    private final CategoryServiceImpl categoryService;

    @GetMapping("/{id}")
    public ResponseDto<Page<ProductDto>> get(@PathVariable Integer id,
                                             @RequestParam(required = false) String sorting,
                                             @RequestParam(required = false) String ordering,
                                             @RequestParam(required = false, defaultValue = "10") Integer size,
                                             @RequestParam(required = false, defaultValue = "0") Integer currentPage) {
        return categoryService.getWithSort(id, sorting, ordering, currentPage);
    }

    @Operation(
            method = "Add category",
            description = "You can add category. Send categoryDto to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Category added",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Add category"

    )

    @PostMapping
    public ResponseDto<CategoryDto> addCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.addCategory(categoryDto);
    }

    @GetMapping("/{id}/brand")
    public ResponseDto<Page<ProductDto>> byBrand(@PathVariable Integer id,
                                                 @RequestParam List<String> filter,
                                                 @RequestParam(required = false) Integer currentPage) {
        return categoryService.getByBrand(id, filter, currentPage);
    }

    @GetMapping("/{id}/brands")
    public ResponseDto<Set<BrandDto>> brands(@PathVariable Integer id) {
        return categoryService.brandByCategory(id);
    }

}
