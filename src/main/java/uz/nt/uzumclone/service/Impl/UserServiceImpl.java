package uz.nt.uzumclone.service.Impl;

import lombok.RequiredArgsConstructor;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.dto.UsersDto;
import uz.nt.uzumclone.model.Users;
import uz.nt.uzumclone.repository.UsersRepository;
import uz.nt.uzumclone.service.UsersService;
import uz.nt.uzumclone.service.mapper.UsersMapper;

import java.util.Optional;

import static uz.nt.uzumclone.additional.AppStatusCodes.*;
import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@RequiredArgsConstructor
public class UserServiceImpl implements UsersService {
    private final UsersMapper userMapper;
    private final UsersRepository usersRepository;
    @Override
    public ResponseDto<UsersDto> addUser(UsersDto dto) {
        try {
            Users users = userMapper.toEntity(dto);
            usersRepository.save(users);

            return ResponseDto.<UsersDto>builder()
                    .success(true)
                    .data(userMapper.toDto(users))
                    .message(OK)
                    .code(OK_CODE)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<UsersDto>builder()
                    .success(false)
                    .message(e.getMessage())
                    .code(DATABASE_ERROR_CODE)
                    .build();
        }
    }

    @Override
    public ResponseDto<UsersDto> updateUser(UsersDto usersDto) {
        if (usersDto.getId() == null){
            return ResponseDto.<UsersDto>builder()
                    .message(NULL_VALUE)
                    .code(VALIDATION_ERROR_CODE)
                    .data(usersDto)
                    .build();
        }

        Optional<Users> userOptional = usersRepository.findByIdAndIsActive(usersDto.getId(), (short) 1);

        if (userOptional.isEmpty()){
            return ResponseDto.<UsersDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_ERROR_CODE)
                    .data(usersDto)
                    .build();
        }
        Users user = userOptional.get();
        if (usersDto.getFirstName() != null) {
            user.setFirstName(usersDto.getFirstName());
        }
        if (usersDto.getLastName() != null) {
            user.setLastName(usersDto.getLastName());
        }
        if (usersDto.getMiddleName() != null) {
            user.setMiddleName(usersDto.getMiddleName());
        }
        if (usersDto.getEmail() != null) {
            user.setEmail(usersDto.getEmail());
        }
        if (usersDto.getGender() != null) {
            user.setGender(usersDto.getGender());
        }

        if (usersDto.getPhoneNumber() != null) {
            user.setPhoneNumber(usersDto.getPhoneNumber());
        }
        if (usersDto.getBirthDate() != null) {
            user.setBirthDate(usersDto.getBirthDate());
        }
        try {
            usersRepository.save(user);

            return ResponseDto.<UsersDto>builder()
                    .data(userMapper.toDto(user))
                    .code(OK_CODE)
                    .success(true)
                    .message(OK)
                    .build();
        }catch (Exception e){
            return ResponseDto.<UsersDto>builder()
                    .data(userMapper.toDto(user))
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<UsersDto> getUserByPhoneNumber(String phoneNumber) {
        try {
            return usersRepository.findFirstByPhoneNumberAndIsActive(phoneNumber, (short) 1)
                    .map(u -> ResponseDto.<UsersDto>builder()
                            .data(userMapper.toDto(u))
                            .success(true)
                            .message(OK)
                            .build())
                    .orElse(ResponseDto.<UsersDto>builder()
                            .message(NOT_FOUND)
                            .code(NOT_FOUND_ERROR_CODE)
                            .build());
        }catch (Exception e){
            return ResponseDto.<UsersDto>builder()
                    .message(e.getMessage())
                    .success(true)
                    .code(DATABASE_ERROR_CODE)
                    .build();
        }
    }

    @Override
    public ResponseDto<UsersDto> getById(Integer id) {
        try {
            return usersRepository.findByIdAndIsActive(id, (short) 1)
                    .map(u -> ResponseDto.<UsersDto>builder()
                            .data(userMapper.toDto(u))
                            .success(true)
                            .message(OK)
                            .build())
                    .orElse(ResponseDto.<UsersDto>builder()
                            .message(NOT_FOUND)
                            .code(NOT_FOUND_ERROR_CODE)
                            .build());
        }catch (Exception e){
            return ResponseDto.<UsersDto>builder()
                    .message(e.getMessage())
                    .success(true)
                    .code(DATABASE_ERROR_CODE)
                    .build();
        }
    }
    @Override
    public ResponseDto<UsersDto> deleteUser(Integer id) {
        Optional<Users> user=usersRepository.findByIdAndIsActive(id,(short)1);
        if(user.isEmpty()) {
            return (ResponseDto.<UsersDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_ERROR_CODE)
                    .build());
        }
        Users delUser= user.get();
        delUser.setIsActive((short) 0);
        try {
            usersRepository.save(delUser);
            return ResponseDto.<UsersDto>builder()
                    .success(true)
                    .message(OK)
                    .data(userMapper.toDto(delUser))
                    .build();

        }catch (Exception e){
            return ResponseDto.<UsersDto>builder()
                    .success(false)
                    .message(e.getMessage())
                    .code(OK_CODE)
                    .build();
        }
    }
}
