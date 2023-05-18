package uz.nt.uzumclone.service;

import org.springframework.data.domain.Page;
import uz.nt.uzumclone.dto.OrderItemsDto;
import uz.nt.uzumclone.dto.ResponseDto;

public interface OrderItemsService {
    ResponseDto<OrderItemsDto> add(OrderItemsDto orderItemsDto);
    ResponseDto<Page<OrderItemsDto>> getAll(Integer page, Integer size);
    ResponseDto<OrderItemsDto> getById(Integer id);
    ResponseDto<Void> delete(Integer id);
    ResponseDto<OrderItemsDto> update(OrderItemsDto orderItemsDto);
}
