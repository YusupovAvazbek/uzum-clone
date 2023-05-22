package uz.nt.uzumclone.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.uzumclone.dto.AddressDto;
import uz.nt.uzumclone.model.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper extends CommonMapper<AddressDto, Address>{
}
