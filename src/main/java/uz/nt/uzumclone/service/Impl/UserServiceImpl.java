package uz.nt.uzumclone.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.nt.uzumclone.additional.AppStatusCodes;
import uz.nt.uzumclone.additional.AppStatusMessages;
import uz.nt.uzumclone.dto.LoginDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.dto.UsersDto;
import uz.nt.uzumclone.model.Users;
import uz.nt.uzumclone.repository.ProductRepository;
import uz.nt.uzumclone.repository.UsersRepository;
import uz.nt.uzumclone.security.JwtService;
import uz.nt.uzumclone.service.CartService;
import uz.nt.uzumclone.service.UsersService;
import uz.nt.uzumclone.service.mapper.UsersMapper;

import java.util.Optional;

import static uz.nt.uzumclone.additional.AppStatusCodes.*;
import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UsersService {
    private final UsersMapper userMapper;
    private final UsersRepository usersRepository;
    private final CartService cartService;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Override
    public ResponseDto<UsersDto> addUser(UsersDto dto) {
        try {
            Users users = userMapper.toEntity(dto);
            usersRepository.save(users);
            cartService.createCart(users);
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
        if (usersDto.getId() == null) {
            return ResponseDto.<UsersDto>builder()
                    .message(NULL_VALUE)
                    .code(VALIDATION_ERROR_CODE)
                    .data(usersDto)
                    .build();
        }

        Optional<Users> userOptional = usersRepository.findByIdAndIsActive(usersDto.getId(), (short) 1);

        if (userOptional.isEmpty()) {
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

        try {
            usersRepository.save(user);

            return ResponseDto.<UsersDto>builder()
                    .data(userMapper.toDto(user))
                    .code(OK_CODE)
                    .success(true)
                    .message(OK)
                    .build();
        } catch (Exception e) {
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            return ResponseDto.<UsersDto>builder()
                    .message(e.getMessage())
                    .success(true)
                    .code(DATABASE_ERROR_CODE)
                    .build();
        }
    }

    @Override
    public ResponseDto<UsersDto> deleteUser(Integer id) {
        Optional<Users> user = usersRepository.findByIdAndIsActive(id, (short) 1);
        if (user.isEmpty()) {
            return (ResponseDto.<UsersDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_ERROR_CODE)
                    .build());
        }
        Users delUser = user.get();
        delUser.setIsActive((short) 0);
        try {
            usersRepository.save(delUser);
            return ResponseDto.<UsersDto>builder()
                    .success(true)
                    .message(OK)
                    .data(userMapper.toDto(delUser))
                    .build();

        } catch (Exception e) {
            return ResponseDto.<UsersDto>builder()
                    .success(false)
                    .message(e.getMessage())
                    .code(OK_CODE)
                    .build();
        }
    }

    @Override
    public ResponseDto<String> login(LoginDto loginDto) {
        UsersDto users = loadUserByUsername(loginDto.getUsername());
        if (!passwordEncoder.matches(loginDto.getPassword(), users.getPassword())){
            return ResponseDto.<String>builder()
                    .message("Password is not correct")
                    .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                    .build();
        }

        return ResponseDto.<String>builder()
                .success(true)
                .message(AppStatusMessages.OK)
                .data(jwtService.generateToken(users))
                .build();
    }

    @Override
    public UsersDto loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> users = usersRepository.findFirstByEmail(username);
        if (users.isEmpty()) throw new UsernameNotFoundException("User with email " + username + " is not found");

        return userMapper.toDto(users.get());
    }
}
