package uz.nt.uzumclone.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumclone.additional.AppStatusMessages;
import uz.nt.uzumclone.dto.AddressDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.dto.UsersDto;
import uz.nt.uzumclone.model.Address;
import uz.nt.uzumclone.model.Users;
import uz.nt.uzumclone.repository.AddressRepository;
import uz.nt.uzumclone.service.AddressService;
import uz.nt.uzumclone.service.mapper.AddressMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static uz.nt.uzumclone.additional.AppStatusCodes.*;
import static uz.nt.uzumclone.additional.AppStatusMessages.*;
import static uz.nt.uzumclone.additional.AppStatusMessages.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    @Override
    public ResponseDto<AddressDto> add(AddressDto addressDto) {
        try {
            Address address = addressMapper.toEntity(addressDto);
            addressRepository.save(address);
            return ResponseDto.<AddressDto>builder()
                    .success(true)
                    .message(OK)
                    .data(addressDto)
                    .build();
        }catch (Exception e){
            return ResponseDto.<AddressDto>builder()
                    .success(true)
                    .message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<List<AddressDto>> getAll() {
        try {
            return ResponseDto.<List<AddressDto>>builder()
                    .message(OK)
                    .success(true)
                    .data(addressRepository.findAll().stream()
                            .map(addressMapper::toDto)
                            .toList())
                    .build();
        } catch (Exception e) {
            return ResponseDto.<List<AddressDto>>builder()
                    .code(UNEXPECTED_ERROR_CODE)
                    .message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<AddressDto> getById(Integer id) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isPresent()) {
            return ResponseDto.<AddressDto>builder()
                    .success(true)
                    .message(OK)
                    .data(addressMapper.toDto(address.get()))
                    .build();
        }
        return ResponseDto.<AddressDto>builder()
                .success(false)
                .message(NOT_FOUND)
                .code(NOT_FOUND_ERROR_CODE)
                .build();
    }


    @Override
    public ResponseDto<Void> delete(Integer id) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isPresent()) {
            addressRepository.deleteById(id);
            return ResponseDto.<Void>builder()
                    .success(true)
                    .message(OK)
                    .build();
        }
        return ResponseDto.<Void>builder()
                .success(false)
                .code(NOT_FOUND_ERROR_CODE)
                .message(NOT_FOUND)
                .build();
    }

    @Override
    public ResponseDto<AddressDto> update(AddressDto addressDto) {
        if (addressDto.getId() == null){
            return ResponseDto.<AddressDto>builder()
                    .message(NULL_VALUE)
                    .code(VALIDATION_ERROR_CODE)
                    .build();
        }

        Optional<Address> addressOptional = addressRepository.findById(addressDto.getId());

        if (addressOptional.isEmpty()){
            return ResponseDto.<AddressDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_ERROR_CODE)
                    .build();
        }
        Address address = addressOptional.get();
        if (addressDto.getCode() != null) {
            address.setCode(addressDto.getCode());
        }if(addressDto.getInfo() != null){
            address.setInfo(addressDto.getInfo());
        }if(addressDto.getPhoneNumber() != null){
            address.setPhoneNumber(addressDto.getPhoneNumber());
        }
        try {
            addressRepository.save(address);
            return ResponseDto.<AddressDto>builder()
                    .data(addressMapper.toDto(address))
                    .code(OK_CODE)
                    .success(true)
                    .message(OK)
                    .build();
        }catch (Exception e){
            return ResponseDto.<AddressDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }
}
