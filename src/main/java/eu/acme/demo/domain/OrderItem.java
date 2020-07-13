package eu.acme.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
public class OrderItem extends AuditableEntity {

    // Modified fetch type to lazy as in fetchOrders we don't need to load eagerly all orderItems
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "unit_price", columnDefinition = "DECIMAL(9,2)", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "units", nullable = false)
    private int units;

    @Column(name = "total_price", columnDefinition = "DECIMAL(9,2)", nullable = false)
    private BigDecimal totalPrice;

}
