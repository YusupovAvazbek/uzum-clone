package uz.nt.uzumclone.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.projections.ProductProjection;
import uz.nt.uzumclone.service.Impl.ProductServiceImpl;

import java.util.List;

import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductResources {

    private final ProductServiceImpl productService;

    @Operation(
            method = "Add product",
            description = "You can add product. Send productDto to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product added",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Add product"

    )
    @PostMapping
    public ResponseDto<ProductDto> addProduct(@RequestBody @Valid ProductDto productDto) {
        return productService.addProduct(productDto);
    }

    @Operation(
            method = "Update product",
            description = "You can update product. Send new productDto to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product updated",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Update product"

    )
    @PatchMapping
    public ResponseDto<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
        return productService.updateProduct(productDto);
    }

    //    @GetMapping
//    public ResponseDto<Page<ProductDto>> getAllProducts(@RequestParam(defaultValue = "10",required = false) Integer size,
//                                                        @RequestParam(defaultValue = "0",required = false) Integer page){
//        return productService.getAllProducts(page, size);
//    }
    @Operation(
            method = "Get products",
            description = "You can get products. Send user ID to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data about user's products",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Get products from user"

    )
    @GetMapping
    public ResponseDto<List<ProductProjection>> getProducts(@RequestParam Integer userId) {
        return productService.getProducts(userId);
    }
    @Operation(
            method = "Get product by ID",
            description = "You can get product. Send product ID to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data about productDto",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Get product by ID"

    )

    @GetMapping("/{id}")
    public ResponseDto<ProductDto> getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    @Operation(
            method = "Get viewed product",
            description = "You can get viewed product from user. Send userID to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Returns >>> List<Product projection>",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Get viewed product from user"

    )
    @GetMapping("viewed")
    public ResponseDto<List<ProductProjection>> getViewedProduct(@RequestParam Integer userId) {
        return productService.getViewedProduct(userId);
    }

}
