package eu.acme.demo.web;

import eu.acme.demo.domain.Order;
import eu.acme.demo.mapper.OrderMapper;
import eu.acme.demo.service.OrderService;
import eu.acme.demo.web.dto.OrderDto;
import eu.acme.demo.web.dto.OrderLiteDto;
import eu.acme.demo.web.dto.OrderRequestDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderAPI {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderAPI(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping
    public List<OrderLiteDto> fetchOrders() {
        // fetch all orders in DB
        List<Order> orderList = orderService.fetchOrders();
        return orderMapper.toOrderLiteDtoList(orderList);
    }

    @GetMapping("/{orderId}")
    public OrderDto fetchOrder(@PathVariable UUID orderId) {
        // fetch specific order from DB
        // if order id not exists then return an HTTP 400 (bad request) with a proper payload
        // that contains an error code and an error message
        Order order = orderService.fetchOrder(orderId);
        return orderMapper.toOrderDto(order);
    }

    @PostMapping
    public OrderDto submitOrder(@RequestBody OrderRequestDto orderRequestDto) {
        // submit a new order
        // if client reference code already exist then return an HTTP 400 (bad request) with a proper payload
        // that contains an error code and an error message
        Order order = orderService.submitOrder(orderMapper.toOrderEntity(orderRequestDto));
        return orderMapper.toOrderDto(order);
    }

}
