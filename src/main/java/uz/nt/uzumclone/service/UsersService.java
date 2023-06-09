package uz.nt.uzumclone.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.dto.UsersDto;


public interface UsersService extends UserDetailsService {
    ResponseDto<UsersDto> addUser(UsersDto dto);

    ResponseDto<UsersDto> updateUser(UsersDto usersDto);

    ResponseDto<UsersDto> getUserByPhoneNumber(String phoneNumber);

    ResponseDto<UsersDto> getById(Integer id);

    ResponseDto<UsersDto> deleteUser(Integer id);
}
