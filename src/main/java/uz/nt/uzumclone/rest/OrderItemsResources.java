package uz.nt.uzumclone.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumclone.dto.OrderItemsDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.service.OrderItemsService;

import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@RestController
@RequestMapping("order-items")
@RequiredArgsConstructor
public class OrderItemsResources {
    private final OrderItemsService orderItemsService;

    @Operation(
            method = "Add",
            description = "You can add orderItems. Send orderItemsDto to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "OrderItems added",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Add orderItems"

    )
    @PostMapping("add")
    public ResponseDto<OrderItemsDto> add(@RequestBody @Valid OrderItemsDto orderItemsDto){
        return orderItemsService.add(orderItemsDto);
    }

    @Operation(
            method = "Get all",
            description = "You can get page of orderItems",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Returns >>> Page<OrderItemsDto>",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Get page of orderItems"

    )
    @GetMapping()
    public ResponseDto<Page<OrderItemsDto>> getAll(@RequestParam(defaultValue = "0") Integer page,
                                                     @RequestParam(defaultValue = "10") Integer size){
        return orderItemsService.getAll(page, size);
    }

    @Operation(
            method = "Get by ID",
            description = "You can get orderItems by ID. Send orderItems ID to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data about orderItemsDto",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Get orderItems by ID"

    )
    @GetMapping("{id}")
    public ResponseDto<OrderItemsDto> getById(@PathVariable Integer id){
        return orderItemsService.getById(id);
    }

    @Operation(
            method = "Update",
            description = "You can update orderItems. Send new orderItems to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "OrderItems updated",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Update orderItems"

    )
    @PatchMapping("update")
    public ResponseDto<OrderItemsDto> update(@RequestBody OrderItemsDto orderItemsDto){
        return orderItemsService.update(orderItemsDto);
    }

    @Operation(
            method = "Delete",
            description = "You can delete orderItems. Send orderItems ID to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "OrderItems deleted",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Delete orderItems by ID"

    )
    @DeleteMapping()
    public ResponseDto<Void> delete(@RequestParam Integer id){
        return orderItemsService.delete(id);
    }
}
