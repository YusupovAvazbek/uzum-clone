package uz.nt.uzumclone.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.projections.ProductProjection;
import uz.nt.uzumclone.service.FavouriteService;

import java.util.List;

import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@RestController
@RequestMapping("/wishes")
@RequiredArgsConstructor
public class FavoriteResources {
    private final FavouriteService favouriteService;

    @Operation(
            method = "Like",
            description = "You can add like. Send userID and productID to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Like added",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Add like to product"

    )

    @PostMapping("/like")
    public ResponseDto<Boolean> like(@RequestParam Integer userId,
                                     @RequestParam Integer productId) {
        return favouriteService.like(userId, productId);
    }

    @Operation(
            method = "Unlike",
            description = "You can delete like. Send userID and productID to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Like deleted",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Delete like from product"

    )
    @DeleteMapping("/like")
    public ResponseDto<Boolean> unlike(@RequestParam Integer userId,
                                       @RequestParam Integer productId) {
        return favouriteService.unlike(userId, productId);
    }

    @Operation(
            method = "Get favourite products",
            description = "You can get favourite products. Send userID to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data about favourite products",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Get favourite products"

    )
    @GetMapping
    public ResponseDto<List<ProductProjection>> getFavoriteProducts(@RequestParam Integer userId) {
        return favouriteService.getFavouriteByUser(userId);
    }
}
