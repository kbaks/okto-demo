package eu.acme.demo.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDto extends OrderLiteDto {

    private List<OrderItemDto> orderItems;

}
