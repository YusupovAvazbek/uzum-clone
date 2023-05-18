package uz.nt.uzumclone.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import uz.nt.uzumclone.dto.OrderItemsDto;
import uz.nt.uzumclone.model.OrderItems;

@Mapper(componentModel = "spring")
public abstract class OrderItemsMapper implements CommonMapper<OrderItemsDto, OrderItems> {

    @Autowired
    protected OrderDetailsMapper orderDetailsMapper;
    @Autowired
    protected ProductMapper productMapper;

    @Override
    @Mapping(target = "order", expression = "java(orderDetailsMapper.toDto(orderItems.getOrder()))")
    @Mapping(target = "product", expression = "java(productMapper.toDto(orderItems.getProduct()))")
    public abstract OrderItemsDto toDto(OrderItems orderItems);
}
