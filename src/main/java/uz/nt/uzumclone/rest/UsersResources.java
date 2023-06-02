package uz.nt.uzumclone.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.dto.UsersDto;
import uz.nt.uzumclone.service.UsersService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersResources {
    private final UsersService usersService;
    @PostMapping
    public ResponseDto<UsersDto> addUsers(@RequestBody UsersDto usersDto) {
        return usersService.addUser(usersDto);
    }
    @PatchMapping
    public ResponseDto<UsersDto> updateUser(@RequestBody UsersDto usersDto){
        return usersService.updateUser(usersDto);
    }
    @GetMapping("/by-phone-number")
    public ResponseDto<UsersDto> getUserByPhoneNumber(@RequestParam String phoneNumber){
        return usersService.getUserByPhoneNumber(phoneNumber);
    }
    @DeleteMapping("/{id}")
    public ResponseDto<UsersDto> deleteUser(@PathVariable Integer id){
        return  usersService.deleteUser(id);
    }
    @GetMapping("/{id}")
    public ResponseDto<UsersDto> getById(@PathVariable Integer id){
        return usersService.getById(id);
    }
    @PostMapping("/like")
    public ResponseDto<Boolean> like(@RequestParam Integer userId,
                                  @RequestParam Integer productId){
        return usersService.like(userId,productId);
    }

}
