package uz.nt.uzumclone.service;
import uz.nt.uzumclone.dto.AddressDto;
import uz.nt.uzumclone.dto.OrderItemsDto;
import uz.nt.uzumclone.dto.ResponseDto;

import java.util.List;

public interface AddressService {
    ResponseDto<AddressDto> add(AddressDto addressDto);
    ResponseDto<List<AddressDto>> getAll();
    ResponseDto<AddressDto> getById(Integer id);
    ResponseDto<Void> delete(Integer id);
    ResponseDto<AddressDto> update(AddressDto addressDto);
}
