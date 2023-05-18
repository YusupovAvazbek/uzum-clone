package uz.nt.uzumclone.service;

import org.springframework.data.domain.Page;
import uz.nt.uzumclone.dto.OrderDetailsDto;
import uz.nt.uzumclone.dto.ResponseDto;

public interface OrderDetailsService {
    ResponseDto<OrderDetailsDto> add(OrderDetailsDto orderDetailsDto);
    ResponseDto<Page<OrderDetailsDto>> getAll(Integer page, Integer size);
    ResponseDto<OrderDetailsDto> getById(Integer id);
    ResponseDto<Void> delete(Integer id);
    ResponseDto<OrderDetailsDto> update(OrderDetailsDto orderDetailsDto);
}
