package eu.acme.demo.repository;


import eu.acme.demo.domain.Order;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.UUID;

import static eu.acme.demo.util.TestDataUtil.createOrderEntity;
import static eu.acme.demo.util.TestDataUtil.createOrderItemsEntity;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class OrderDataTests {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    public void testCreateOrder() {
        Order order = createOrderEntity("ORDER-1");
        order.addOrderItems(createOrderItemsEntity());
        orderRepository.save(order);

        Assert.notNull(order.getId(), "The order has been saved and it should have a valid UUID as id");
        Optional<Order> orderOptional = orderRepository.findById(order.getId());
        Assert.isTrue(orderOptional.isPresent(), "order not found");

        assertThrows(LazyInitializationException.class, () -> orderOptional.get().getOrderItems().isEmpty(),
                "order items should not be fetched eagerly");
        Assert.isTrue(orderItemRepository.findAllByOrderId(order.getId()).size() == 2,
                "order items not found");

        Assert.isTrue(!orderRepository.findById(UUID.randomUUID()).isPresent(), "non existing order found");
    }

}
