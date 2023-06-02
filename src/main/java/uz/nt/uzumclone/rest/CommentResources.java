package uz.nt.uzumclone.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumclone.dto.CommentDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.service.CommentService;

import java.util.List;

import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@RestController
@RequestMapping("/product/comment")
@RequiredArgsConstructor
public class CommentResources {
    private final CommentService commentService;

    @Operation(
            method = "Get all",
            description = "You can get all comments, send product ID to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "All comments Dtos",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Get all comments"

    )
    @GetMapping("/{id}")
    public ResponseDto<List<CommentDto>> getAll(@PathVariable Integer id) {
        return commentService.viewAll(id);
    }

    @Operation(
            method = "Write comment",
            description = "You can write comment for product, send String(comment) and Product ID to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Comment written",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Write comment for product"

    )
    @PostMapping("/{id}")
    public ResponseDto<CommentDto> writeComment(@RequestParam String comment, @PathVariable Integer id) {
        return commentService.addComment(id, comment);
    }

    @Operation(
            method = "Delete comment",
            description = "You can delete comment, send comment ID to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Comment deleted",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Delete comment with comment ID"

    )
    @DeleteMapping("/{id}")
    public ResponseDto<CommentDto> deleteComment(@PathVariable Integer id) {
        return commentService.removeComment(id);
    }

}