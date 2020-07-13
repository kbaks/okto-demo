package eu.acme.demo.mapper;

import eu.acme.demo.domain.Order;
import eu.acme.demo.web.dto.OrderDto;
import eu.acme.demo.web.dto.OrderLiteDto;
import eu.acme.demo.web.dto.OrderRequestDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private final ModelMapper modelMapper;
    private final OrderItemMapper orderItemMapper;

    public OrderMapper(ModelMapper modelMapper, OrderItemMapper orderItemMapper) {
        this.modelMapper = modelMapper;
        this.orderItemMapper = orderItemMapper;
    }

    public List<OrderLiteDto> toOrderLiteDtoList(List<Order> orderList) {
        return orderList.stream()
                .map(order -> modelMapper.map(order, OrderLiteDto.class))
                .collect(Collectors.toList());
    }

    public OrderDto toOrderDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

    public Order toOrderEntity(OrderRequestDto orderRequestDto) {
        Order order = modelMapper
                .getTypeMap(OrderRequestDto.class, Order.class, "skipOrderItemsMap")
                .map(orderRequestDto);
        orderItemMapper
                .toOrderItemEntityList(orderRequestDto.getOrderItems())
                .forEach(order::addOrderItem);
        return order;
    }
}
