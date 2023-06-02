package uz.nt.uzumclone.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.nt.uzumclone.dto.OrderDetailsDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.model.OrderDetails;
import uz.nt.uzumclone.repository.OrderDetailsRepository;
import uz.nt.uzumclone.service.OrderDetailsService;
import uz.nt.uzumclone.service.mapper.OrderDetailsMapper;
import uz.nt.uzumclone.service.mapper.PaymentDetailsMapper;
import uz.nt.uzumclone.service.mapper.UsersMapper;

import java.util.Optional;

import static uz.nt.uzumclone.additional.AppStatusCodes.*;
import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final OrderDetailsMapper orderDetailsMapper;
    private final OrderDetailsRepository orderDetailsRepository;
    private final UsersMapper usersMapper;
    private final PaymentDetailsMapper paymentDetailsMapper;

    @Override
    public ResponseDto<OrderDetailsDto> add(OrderDetailsDto orderDetailsDto) {
        try{
            OrderDetails orderDetails = orderDetailsMapper.toEntity(orderDetailsDto);
            orderDetailsRepository.save(orderDetails);

            return ResponseDto.<OrderDetailsDto>builder()
                    .success(true)
                    .message(OK)
                    .data(orderDetailsMapper.toDto(orderDetails))
                    .build();
        } catch (Exception e){
            return ResponseDto.<OrderDetailsDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + " -> " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<Page<OrderDetailsDto>> getAll(Integer page, Integer size) {
        Long count = orderDetailsRepository.count();

        PageRequest pageRequest = PageRequest.of(
                (count / size) <= page ?
                        (count % size == 0 ? (int) (count / size) - 1
                                : (int) (count / size))
                        : page,
                size);

        Page<OrderDetailsDto> orderDetailsDtos = orderDetailsRepository.findAll(pageRequest).
                map(orderDetailsMapper::toDto);

        return ResponseDto.<Page<OrderDetailsDto>>builder()
                .message(OK)
                .success(true)
                .data(orderDetailsDtos)
                .build();
    }

    @Override
    public ResponseDto<OrderDetailsDto> getById(Integer id) {
        if(id == null){
            return ResponseDto.<OrderDetailsDto>builder()
                    .code(VALIDATION_ERROR_CODE)
                    .message(NULL_VALUE)
                    .build();
        }

        return orderDetailsRepository.findById(id)
                .map(o -> ResponseDto.<OrderDetailsDto>builder()
                        .success(true)
                        .message(OK)
                        .data(orderDetailsMapper.toDto(o))
                        .build())
                .orElse(ResponseDto.<OrderDetailsDto>builder()
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

        orderDetailsRepository.deleteById(id);

        return ResponseDto.<Void>builder()
                .success(true)
                .message(OK)
                .build();
    }

    @Override
    public ResponseDto<OrderDetailsDto> update(OrderDetailsDto orderDetailsDto) {
        if(orderDetailsDto.getId() == null){
            return ResponseDto.<OrderDetailsDto>builder()
                    .code(VALIDATION_ERROR_CODE)
                    .message(NULL_VALUE)
                    .build();
        }

        Optional<OrderDetails> optional = orderDetailsRepository.findById(orderDetailsDto.getId());

        if(optional.isEmpty()){
            return ResponseDto.<OrderDetailsDto>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .build();
        }

        OrderDetails orderDetails = optional.get();

        if(orderDetailsDto.getUser() != null){
            orderDetails.setUser(usersMapper.toEntity(orderDetailsDto.getUser()));
        }
        if(orderDetailsDto.getTotal() != null){
            orderDetails.setTotal(orderDetails.getTotal());
        }
        if(orderDetailsDto.getPayment() != null){
            orderDetails.setPaymentDetails(paymentDetailsMapper.toEntity(orderDetailsDto.getPayment()));
        }
        if(orderDetailsDto.getCreatedAt() != null){
            orderDetails.setCreatedAt(orderDetails.getCreatedAt());
        }
        if(orderDetailsDto.getModifiedAt() != null){
            orderDetails.setModifiedAt(orderDetails.getModifiedAt());
        }

        orderDetailsRepository.save(orderDetails);

        return ResponseDto.<OrderDetailsDto>builder()
                .message(OK)
                .success(true)
                .data(orderDetailsMapper.toDto(orderDetails))
                .build();
    }
}
