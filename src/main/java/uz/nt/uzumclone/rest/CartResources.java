package uz.nt.uzumclone.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumclone.dto.CartDto;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.service.Impl.CartServiceImpl;

import java.util.List;

import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartResources {
    private final CartServiceImpl cartService;

    @Operation(
            method = "Get Cart User",
            description = "You can get cart of user, send User ID to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data about User cart",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Get Cart of User"

    )
    @GetMapping("/{id}")
    public ResponseDto<List<ProductDto>> getCartUser(@PathVariable Integer id) {
        return cartService.getUserCart(id);
    }

    @Operation(
            method = "Add to Cart",
            description = "You can add product to cart. Send cart ID and product ID to do this.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product added to cart",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Add product to cart"

    )
    @PostMapping
    public ResponseDto<CartDto> addToCart(@RequestParam Integer cartId, @RequestParam Integer productId) {
        return cartService.addToCart(cartId, productId);
    }
}