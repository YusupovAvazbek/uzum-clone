package uz.nt.uzumclone.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.uzumclone.dto.CartDto;
import uz.nt.uzumclone.model.Cart;

@Mapper(componentModel = "spring")
public interface CartMapper extends CommonMapper<CartDto, Cart> {
}
