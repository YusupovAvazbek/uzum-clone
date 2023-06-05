package uz.nt.uzumclone.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumclone.dto.OrderDetailsDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.service.Impl.OrderDetailsServiceImpl;

import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@RestController
@RequestMapping("order-details")
@RequiredArgsConstructor
public class OrderDetailsResources {

    private final OrderDetailsServiceImpl orderDetailsService;

    @Operation(
            method = "Add",
            description = "You can add order. Send orderDto to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Order added",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Add order"

    )

    @PostMapping("add")
    public ResponseDto<OrderDetailsDto> add(@RequestBody @Valid OrderDetailsDto orderDetailsDto){
        return orderDetailsService.add(orderDetailsDto);
    }

    @GetMapping()
    public ResponseDto<Page<OrderDetailsDto>> getAll(@RequestParam(defaultValue = "0") Integer page,
                                                     @RequestParam(defaultValue = "10") Integer size){
        return orderDetailsService.getAll(page, size);
    }

    @Operation(
            method = "Get by ID",
            description = "You can get order by ID. Send order ID to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data about order",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Get order by ID"

    )

    @GetMapping("{id}")
    public ResponseDto<OrderDetailsDto> getById(@PathVariable Integer id){
        return orderDetailsService.getById(id);
    }

    @Operation(
            method = "Update",
            description = "You can update order. Send new orderDto to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Order updated",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Update order"

    )

    @PatchMapping("update")
    public ResponseDto<OrderDetailsDto> update(@RequestBody OrderDetailsDto orderDetailsDto){
        return orderDetailsService.update(orderDetailsDto);
    }

    @Operation(
            method = "Delete order",
            description = "You can delete order. Send order ID to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Order deleted",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Delete order by ID"

    )

    @DeleteMapping()
    public ResponseDto<Void> delete(@RequestParam Integer id){
        return orderDetailsService.delete(id);
    }

}
