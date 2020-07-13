package eu.acme.demo.service;

import eu.acme.demo.domain.Order;
import eu.acme.demo.exception.ExistingOrderException;
import eu.acme.demo.exception.ResourceNotFoundException;
import eu.acme.demo.exception.ResourceType;
import eu.acme.demo.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public List<Order> fetchOrders() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Order fetchOrder(UUID orderId) {
        return orderRepository
                .findByIdAndFetchItemsEagerly(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.ORDER, orderId.toString()));
    }

    @Transactional
    public Order submitOrder(Order order) {
        if (orderRepository.findByClientReferenceCode(order.getClientReferenceCode()).isPresent()) {
            throw new ExistingOrderException(order.getClientReferenceCode());
        }
        return orderRepository.save(order);
    }
}
