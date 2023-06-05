package uz.nt.uzumclone.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumclone.dto.AddressDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.service.AddressService;

import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressResources {
    private final AddressService addressService;

    @Operation(
            method = "Add address",
            description = "You can add address. Send addressDto to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Address added",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Add address"

    )

    @PostMapping
    public ResponseDto<AddressDto> addAddress(@RequestBody AddressDto addressDto) {
        return addressService.add(addressDto);
    }

    @Operation(
            method = "Update address",
            description = "You can update address. Send new addressDto to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Address updated",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Update address"

    )

    @PatchMapping
    public ResponseDto<AddressDto> updateAddress(@RequestBody AddressDto addressDto) {
        return addressService.update(addressDto);
    }

    @Operation(
            method = "Delete address",
            description = "You can delete address. Send address ID to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Address deleted",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Delete address"

    )
    @DeleteMapping("/{id}")
    public ResponseDto<Void> deleteAddress(@PathVariable Integer id) {
        return addressService.delete(id);
    }

    @Operation(
            method = "Get address by ID",
            description = "You can get address. Send address ID to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data about addressDto",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Get address by ID"

    )
    @GetMapping("/{id}")
    public ResponseDto<AddressDto> getById(@PathVariable Integer id) {
        return addressService.getById(id);
    }
}
