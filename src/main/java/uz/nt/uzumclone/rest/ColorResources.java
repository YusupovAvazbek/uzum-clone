package uz.nt.uzumclone.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumclone.dto.ColorDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.service.Impl.ColorServiceImpl;

import java.util.List;

import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@RestController
@RequestMapping("color")
@RequiredArgsConstructor
public class ColorResources {
    private final ColorServiceImpl colorService;

    @Operation(
            method = "Add color",
            description = "You can add color. Send colorDto to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Color added",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Add color"

    )

    @PostMapping("add")
    public ResponseDto<ColorDto> add(@RequestBody ColorDto colorDto) {
        return colorService.add(colorDto);
    }

    @Operation(
            method = "Get by ID",
            description = "You can get color by ID. Send color ID to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data about colorDto",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Get color by ID"

    )

    @GetMapping("{id}")
    public ResponseDto<ColorDto> getById(@PathVariable Integer id) {
        return colorService.getById(id);
    }

    @Operation(
            method = "Get all",
            description = "You can get all color.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data about all color",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Get all color"

    )

    @GetMapping()
    public ResponseDto<List<ColorDto>> getAll() {
        return colorService.getAll();
    }
}
