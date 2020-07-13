package eu.acme.demo.web.dto;

import eu.acme.demo.domain.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class OrderLiteDto {

    private UUID id;
    private OrderStatus status;
    private String description;
    /**
     * reference code used by client system to track order
     */
    private String clientReferenceCode;
    private BigDecimal totalAmount;
    private int itemCount;
    private Instant createdDate;
    private Instant updatedDate;

}
