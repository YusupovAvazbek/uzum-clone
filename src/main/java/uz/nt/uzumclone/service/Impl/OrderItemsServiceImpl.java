package uz.nt.uzumclone.service.Impl;

import org.springframework.data.domain.Page;
import uz.nt.uzumclone.dto.OrderItemsDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.service.OrderItemsService;

public class OrderItemsServiceImpl implements OrderItemsService {
    @Override
    public ResponseDto<OrderItemsDto> add(OrderItemsDto orderItemsDto) {
        return null;
    }

    @Override
    public ResponseDto<Page<OrderItemsDto>> getAll(Integer page, Integer size) {
        return null;
    }

    @Override
    public ResponseDto<OrderItemsDto> getById(Integer id) {
        return null;
    }

    @Override
    public ResponseDto<Void> delete(Integer id) {
        return null;
    }

    @Override
    public ResponseDto<OrderItemsDto> update(OrderItemsDto orderItemsDto) {
        return null;
    }
}
