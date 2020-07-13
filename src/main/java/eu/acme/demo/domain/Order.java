package eu.acme.demo.domain;

import eu.acme.demo.domain.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends AuditableEntity {

    @Column(name = "ref_code", length = 30, nullable = false, unique = true)
    private String clientReferenceCode;

    @Column(name = "description")
    private String description;

    @Column(name = "total_amount", columnDefinition = "DECIMAL(9,2)", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "item_count", nullable = false)
    private int itemCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private OrderStatus status;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Setter(AccessLevel.NONE)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItems(List<OrderItem> orderItems) {
        orderItems.forEach(this::addOrderItem);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        orderItem.setOrder(null);
    }
}
