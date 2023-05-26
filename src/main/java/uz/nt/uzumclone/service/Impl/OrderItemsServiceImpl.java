package uz.nt.uzumclone.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.nt.uzumclone.dto.OrderItemsDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.model.OrderItems;
import uz.nt.uzumclone.repository.OrderItemsRepository;
import uz.nt.uzumclone.service.OrderItemsService;
import uz.nt.uzumclone.service.mapper.OrderDetailsMapper;
import uz.nt.uzumclone.service.mapper.OrderItemsMapper;
import uz.nt.uzumclone.service.mapper.ProductMapper;

import java.util.Optional;

import static uz.nt.uzumclone.additional.AppStatusCodes.*;
import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class OrderItemsServiceImpl implements OrderItemsService {

    private final OrderItemsMapper orderItemsMapper;
    private final OrderItemsRepository orderItemsRepository;
    private final OrderDetailsMapper orderDetailsMapper;
    private final ProductMapper productMapper;

    @Override
    public ResponseDto<OrderItemsDto> add(OrderItemsDto orderItemsDto) {
        try{
            OrderItems orderItems = orderItemsMapper.toEntity(orderItemsDto);
            orderItemsRepository.save(orderItems);

            return ResponseDto.<OrderItemsDto>builder()
                    .message(OK)
                    .success(true)
                    .data(orderItemsMapper.toDto(orderItems))
                    .build();
        } catch (Exception e){
            return ResponseDto.<OrderItemsDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + " -> " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<Page<OrderItemsDto>> getAll(Integer page, Integer size) {
        Long count = orderItemsRepository.count();

        PageRequest pageRequest = PageRequest.of(
                (count / size) <= page ?
                        (count % size == 0 ? (int) (count / size) - 1
                                : (int) (count / size))
                        : page,
        size);

        Page<OrderItemsDto> orderItemsDtos = orderItemsRepository.findAll(pageRequest)
                .map(orderItemsMapper::toDto);

        return ResponseDto.<Page<OrderItemsDto>>builder()
                .success(true)
                .message(OK)
                .data(orderItemsDtos)
                .build();
    }

    @Override
    public ResponseDto<OrderItemsDto> getById(Integer id) {
        if(id == null){
            return ResponseDto.<OrderItemsDto>builder()
                    .code(VALIDATION_ERROR_CODE)
                    .message(NULL_VALUE)
                    .build();
        }

        return orderItemsRepository.findById(id)
                .map(o -> ResponseDto.<OrderItemsDto>builder()
                        .success(true)
                        .message(OK)
                        .data(orderItemsMapper.toDto(o))
                        .build())
                .orElse(ResponseDto.<OrderItemsDto>builder()
                        .code(NOT_FOUND_ERROR_CODE)
                        .message(NOT_FOUND)
                        .build());
    }

    @Override
    public ResponseDto<Void> delete(Integer id) {
        if(id == null){
            return ResponseDto.<Void>builder()
                    .code(VALIDATION_ERROR_CODE)
                    .message(NULL_VALUE)
                    .build();
        }

        orderItemsRepository.deleteById(id);

        return ResponseDto.<Void>builder()
                .success(true)
                .message(OK)
                .build();
    }

    @Override
    public ResponseDto<OrderItemsDto> update(OrderItemsDto orderItemsDto) {
        if(orderItemsDto.getId() == null){
            return ResponseDto.<OrderItemsDto>builder()
                    .code(VALIDATION_ERROR_CODE)
                    .message(NULL_VALUE)
                    .build();
        }

        Optional<OrderItems> optional = orderItemsRepository.findById(orderItemsDto.getId());

        if(optional.isEmpty()){
            return ResponseDto.<OrderItemsDto>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .build();
        }

        OrderItems orderItems = optional.get();

        if (orderItemsDto.getOrder() != null){
            orderItems.setOrder(orderDetailsMapper.toEntity(orderItemsDto.getOrder()));
        }
        if (orderItemsDto.getProduct() != null){
            orderItems.setProduct(productMapper.toEntity(orderItemsDto.getProduct()));
        }
        if (orderItemsDto.getQuantity() != null){
            orderItems.setQuantity(orderItems.getQuantity());
        }
        if (orderItemsDto.getCreatedAt() != null){
            orderItems.setCreatedAt(orderItemsDto.getCreatedAt());
        }
        if (orderItemsDto.getModifiedAt() != null){
            orderItems.setModifiedAt(orderItemsDto.getModifiedAt());
        }

        orderItemsRepository.save(orderItems);

        return ResponseDto.<OrderItemsDto>builder()
                .success(true)
                .message(OK)
                .data(orderItemsMapper.toDto(orderItems))
                .build();
    }
}
