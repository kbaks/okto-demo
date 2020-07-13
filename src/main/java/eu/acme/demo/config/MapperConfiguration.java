package eu.acme.demo.config;

import eu.acme.demo.domain.Order;
import eu.acme.demo.web.dto.OrderRequestDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper
                .createTypeMap(OrderRequestDto.class, Order.class, "skipOrderItemsMap")
                .addMappings(mapper -> mapper.skip(Order::addOrderItems));

        /*modelMapper
                .createTypeMap(OrderItem.class, OrderItemDto.class, "mapIdToItemId")
                .addMappings(mapper -> mapper.map(PersistableEntity::getId, OrderItemDto::setId));*/

        return modelMapper;
    }

}
