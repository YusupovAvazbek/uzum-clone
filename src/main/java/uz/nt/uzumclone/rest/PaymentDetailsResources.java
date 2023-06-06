package uz.nt.uzumclone.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumclone.dto.PaymentDetailsDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.service.Impl.PaymentDetailsServiceImpl;

import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@RestController
@RequestMapping("payment-details")
@RequiredArgsConstructor
public class PaymentDetailsResources {

    private final PaymentDetailsServiceImpl paymentDetailsService;

    @Operation(
            method = "Add",
            description = "You can add payment. Send paymentDto to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Payment added",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Add payment"

    )
    @PostMapping("add")
    public ResponseDto<PaymentDetailsDto> add(@RequestBody @Valid PaymentDetailsDto paymentDetailsDto){
        return paymentDetailsService.add(paymentDetailsDto);
    }

    @GetMapping()
    public ResponseDto<Page<PaymentDetailsDto>> getAll(@RequestParam(defaultValue = "0") Integer page,
                                                     @RequestParam(defaultValue = "10") Integer size){
        return paymentDetailsService.getAll(page, size);
    }

    @Operation(
            method = "Get by ID",
            description = "You can get payment by ID. Send payment ID to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data about payment",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Get payment by ID"

    )
    @GetMapping("{id}")
    public ResponseDto<PaymentDetailsDto> getById(@PathVariable Integer id){
        return paymentDetailsService.getById(id);
    }

    @Operation(
            method = "Update",
            description = "You can update payment. Send new paymentDto to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Payment updated",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Update payment"

    )
    @PatchMapping("update")
    public ResponseDto<PaymentDetailsDto> update(@RequestBody PaymentDetailsDto paymentDetailsDto){
        return paymentDetailsService.update(paymentDetailsDto);
    }

    @Operation(
            method = "Delete",
            description = "You can delete payment. Send payment ID to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Payment deleted",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Delete payment"

    )
    @DeleteMapping()
    public ResponseDto<Void> delete(@RequestParam Integer id){
        return paymentDetailsService.delete(id);
    }
}
