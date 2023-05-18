package uz.nt.uzumclone.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import uz.nt.uzumclone.dto.OrderDetailsDto;
import uz.nt.uzumclone.model.OrderDetails;

@Mapper(componentModel = "spring")
public abstract class OrderDetailsMapper implements CommonMapper<OrderDetailsDto, OrderDetails> {

    @Autowired
    protected UsersMapper usersMapper;
    @Autowired
    protected PaymentDetailsMapper paymentDetailsMapper;

    @Override
    @Mapping(target = "user", expression = "java(usersMapper.toDto(orderDetails.getUser()))")
    @Mapping(target = "payment", expression = "java(paymentDetailsMapper.toDto(orderDetails.getPaymentDetails()))")
    public abstract OrderDetailsDto toDto(OrderDetails orderDetails);
}
