package uz.nt.uzumclone.service.Impl;

import org.springframework.data.domain.Page;
import uz.nt.uzumclone.dto.OrderDetailsDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.service.OrderDetailsService;

public class OrderDetailsServiceImpl implements OrderDetailsService {
    @Override
    public ResponseDto<OrderDetailsDto> add(OrderDetailsDto orderDetailsDto) {
        return null;
    }

    @Override
    public ResponseDto<Page<OrderDetailsDto>> getAll(Integer page, Integer size) {
        return null;
    }

    @Override
    public ResponseDto<OrderDetailsDto> getById(Integer id) {
        return null;
    }

    @Override
    public ResponseDto<Void> delete(Integer id) {
        return null;
    }

    @Override
    public ResponseDto<OrderDetailsDto> update(OrderDetailsDto orderDetailsDto) {
        return null;
    }
}
