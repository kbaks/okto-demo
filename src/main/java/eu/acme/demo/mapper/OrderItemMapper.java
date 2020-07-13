package eu.acme.demo.mapper;

import eu.acme.demo.domain.OrderItem;
import eu.acme.demo.web.dto.OrderItemDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderItemMapper {

    private final ModelMapper modelMapper;

    public OrderItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<OrderItem> toOrderItemEntityList(List<OrderItemDto> orderItems) {
        return orderItems.stream()
                .map(orderItemDto -> modelMapper.map(orderItemDto, OrderItem.class))
                .collect(Collectors.toList());
    }
}
