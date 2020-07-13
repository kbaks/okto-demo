package eu.acme.demo.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class OrderItemDto {

    // Renamed itemId to id so that it matches the database column
    // If this should not be changed we could create a type map in modelMapper
    // as it can be seen in commented snippet in eu.acme.demo.config.MapperConfiguration.modelMapper
    private UUID id;
    private int units;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private Instant createdDate;
    private Instant updatedDate;

}
