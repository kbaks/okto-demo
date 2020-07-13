package eu.acme.demo.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto extends OrderDto {

    // SHOULD_DO: place required fields in order to create an order submitted by client
    // DID: by extending OrderDto we have all required fields - we could also use OrderDto without extending it
    // clientReferenceCode was removed as it already exists in OrderLiteDto

}
