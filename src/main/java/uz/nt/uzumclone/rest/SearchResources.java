package uz.nt.uzumclone.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.service.Impl.ProductServiceImpl;

import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchResources {
    private final ProductServiceImpl productService;

    @Operation(
            method = "Search",
            description = "You get product page",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product page",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Get product page"

    )
    @GetMapping()
    public ResponseDto<Page<ProductDto>> search(@RequestParam String query,
                                                @RequestParam(required = false) String sorting,
                                                @RequestParam(required = false) String ordering,
                                                @RequestParam(required = false,defaultValue = "10") Integer size,
                                                @RequestParam(required = false, defaultValue = "0") Integer currentPage){
        return productService.universalSearch(query, sorting, ordering,size,currentPage);
    }
}
