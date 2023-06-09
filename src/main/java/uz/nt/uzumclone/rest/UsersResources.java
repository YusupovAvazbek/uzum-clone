package uz.nt.uzumclone.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumclone.dto.LoginDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.dto.UsersDto;
import uz.nt.uzumclone.service.UsersService;

import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersResources {
    private final UsersService usersService;
    @Operation(
            method = "Add user",
            description = "You can add user, send userDto to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User added",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Add user"

    )
    @PostMapping
    public ResponseDto<UsersDto> addUsers(@RequestBody UsersDto usersDto) {
        return usersService.addUser(usersDto);
    }
    @Operation(
            method = "Update user",
            description = "You can update user, send new UserDto to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User updated",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Update user"

    )
    @PatchMapping
    public ResponseDto<UsersDto> updateUser(@RequestBody UsersDto usersDto){
        return usersService.updateUser(usersDto);
    }
    @Operation(
            method = "Get user by phone number",
            description = "You can get user by phone number, send String(phone number) to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data about userDto",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Get user by phone number"

    )
    @GetMapping("/by-phone-number")
    public ResponseDto<UsersDto> getUserByPhoneNumber(@RequestParam String phoneNumber){
        return usersService.getUserByPhoneNumber(phoneNumber);
    }
    @Operation(
            method = "Delete user",
            description = "You can delete user, send user ID to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User deleted",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Delete user by id"

    )
    @DeleteMapping("/{id}")
    public ResponseDto<UsersDto> deleteUser(@PathVariable Integer id){
        return  usersService.deleteUser(id);
    }
    @Operation(
            method = "Get by ID",
            description = "You can get user by ID, send User ID to this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data about userDto",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "-2", description = VALIDATION_ERROR),
                    @ApiResponse(responseCode = "-1", description = NOT_FOUND),
                    @ApiResponse(responseCode = "0", description = OK),
                    @ApiResponse(responseCode = "1", description = DATABASE_ERROR),
                    @ApiResponse(responseCode = "2", description = UNEXPECTED_ERROR)
            },
            summary = "Get user by ID"

    )
    @GetMapping("/{id}")
    public ResponseDto<UsersDto> getById(@PathVariable Integer id){
        return usersService.getById(id);
    }

    @PostMapping("sign-in")
    public ResponseDto<String> login(@RequestBody LoginDto loginDto){
        return usersService.login(loginDto);
    }

}