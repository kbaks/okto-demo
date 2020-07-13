package eu.acme.demo.util;

import eu.acme.demo.domain.Order;
import eu.acme.demo.domain.OrderItem;
import eu.acme.demo.domain.enums.OrderStatus;
import eu.acme.demo.web.dto.OrderItemDto;
import eu.acme.demo.web.dto.OrderRequestDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestDataUtil {

    public static OrderRequestDto getOrderRequestDto(String clientReferenceCode) {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setTotalPrice(BigDecimal.valueOf(31.30));
        orderItemDto.setUnitPrice(BigDecimal.valueOf(15.65));
        orderItemDto.setUnits(2);
        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        orderItemDtoList.add(orderItemDto);
        orderRequestDto.setClientReferenceCode(clientReferenceCode);
        orderRequestDto.setOrderItems(orderItemDtoList);
        orderRequestDto.setItemCount(2);
        orderRequestDto.setStatus(OrderStatus.SUBMITTED);
        orderRequestDto.setTotalAmount(BigDecimal.valueOf(31.30));
        return orderRequestDto;
    }

    public static Order createOrderEntity(String clientReferenceCode) {
        Order order = new Order();
        order.setStatus(OrderStatus.SUBMITTED);
        order.setClientReferenceCode(clientReferenceCode);
        order.setDescription("first order");
        order.setItemCount(10);
        order.setTotalAmount(BigDecimal.valueOf(100.23));
        return order;
    }

    public static List<OrderItem> createOrderItemsEntity() {
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(createOrderItemEntity());
        orderItems.add(createOrderItemEntity());
        return orderItems;
    }

    protected static OrderItem createOrderItemEntity() {
        OrderItem orderItem = new OrderItem();
        orderItem.setTotalPrice(BigDecimal.valueOf(31.30));
        orderItem.setUnitPrice(BigDecimal.valueOf(15.65));
        orderItem.setUnits(2);
        return orderItem;
    }

}
