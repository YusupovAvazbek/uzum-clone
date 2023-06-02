package uz.nt.uzumclone.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.service.ProductService;

import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductResources {

    private final ProductService productService;

    @Operation(
            method = "Add product",
            description = "You can add product, send ProductDto to this method",
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
            description = "You can update product, send new productDto to this method",
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

    @Operation(
            method = "Get all products",
            description = "You can get all products",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data about all product",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Get all products"

    )
    @GetMapping
    public ResponseDto<Page<ProductDto>> getAllProducts(@RequestParam(defaultValue = "10") Integer size,
                                                        @RequestParam(defaultValue = "0") Integer page) {
        return productService.getAllProducts(page, size);
    }

    @Operation(
            method = "Get product by ID",
            description = "You can get cart product by id, send product ID to this method",
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
    @GetMapping("/by-id")
    public ResponseDto<ProductDto> getProductById(@RequestParam Integer id) {
        return productService.getProductById(id);
    }
}