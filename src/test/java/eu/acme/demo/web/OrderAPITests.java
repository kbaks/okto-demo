package eu.acme.demo.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.acme.demo.domain.Order;
import eu.acme.demo.repository.OrderRepository;
import eu.acme.demo.web.dto.OrderDto;
import eu.acme.demo.web.dto.OrderLiteDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static eu.acme.demo.util.TestDataUtil.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class OrderAPITests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void testOrderAPI() throws Exception {
        // submit order request
        OrderDto orderDto = submitOrder("CREATE-ORDER-TEST", status().isOk());
        // make sure OrderDto contains correct data
        Assert.isTrue(orderDto.getOrderItems().size() == 1, "order items different than expected.");
        Assert.notNull(orderDto.getId(), "order id should have the generated UUID.");
    }

    @Test
    void testOrderDoubleSubmission() throws Exception {
        submitOrder("CREATE-ORDER-DOUBLE", status().isOk());
        // trigger validation error when submit the same order twice (same client reference code)
        submitOrder("CREATE-ORDER-DOUBLE", status().isBadRequest());
    }

    @Test
    void testFetchAllOrders() throws Exception {
        // delete all existing orders from database
        orderRepository.deleteAll();
        // create 2 orders (by directly saving to database)
        saveOrder("CREATE-ORDER-FETCH-1");
        saveOrder("CREATE-ORDER-FETCH-2");
        // invoke API call to fetch all orders check that response contains 2 orders
        Assert.isTrue(fetchOrders(status().isOk()).size() == 2, "two orders should exist.");
    }

    @Test
    void testFetchCertainOrder() throws Exception {
        // create 1 order (by directly saving to database) and then invoke API call to fetch order
        Order order = saveOrder("CREATE-ORDER-FETCH-CERTAIN");
        Assert.notNull(order.getId(), "The order has been saved and it should have a valid UUID as id.");
        OrderDto orderDto = fetchOrder(order.getId(), status().isOk());
        // check response contains the correct order
        Assertions.assertEquals(order.getClientReferenceCode(), orderDto.getClientReferenceCode());
        Assert.isTrue(!order.getOrderItems().isEmpty(), "order items not found");
    }

    @Test
    void testFetchOrderNotExists() throws Exception {
        // check that when an order not exists, server responds with http 400
        fetchOrder(UUID.randomUUID(), status().isNotFound());
    }

    private List<OrderLiteDto> fetchOrders(ResultMatcher expectedResult) throws Exception {
        MvcResult fetchOrdersResult = performAndAssertRequest(
                get("/orders"), expectedResult);
        return asObject(fetchOrdersResult.getResponse().getContentAsString(), new TypeReference<List<OrderLiteDto>>() {});
    }

    private OrderDto fetchOrder(UUID orderId, ResultMatcher expectedResult) throws Exception {
        MvcResult fetchOrderResult = performAndAssertRequest(
                get("/orders/" + orderId.toString()), expectedResult);
        return asObject(fetchOrderResult.getResponse().getContentAsString(), OrderDto.class);
    }

    private OrderDto submitOrder(String clientReferenceCode, ResultMatcher expectedResult) throws Exception {
        // 1. create order request
        // 2. convert to json string using Jackson Object Mapper
        // 3. set json string to content param
        MvcResult orderResult = performAndAssertRequest(
                post("/orders").content(getOrderRequestAsString(clientReferenceCode)), expectedResult);
        // retrieve order dto from response
        // convert orderResult.getResponse().getContentAsString() to OrderDto using Jackson Object Mapper
        return asObject(orderResult.getResponse().getContentAsString(), OrderDto.class);
    }

    private MvcResult performAndAssertRequest(MockHttpServletRequestBuilder mockHttpServletRequestBuilder,
                                              ResultMatcher expectedResult) throws Exception {
        return this.mockMvc.perform(mockHttpServletRequestBuilder
                .contentType("application/json")
                .accept("application/json"))
                .andExpect(expectedResult)
                .andReturn();
    }

    private Order saveOrder(String clientReferenceCode) {
        Order order = createOrderEntity(clientReferenceCode);
        order.addOrderItems(createOrderItemsEntity());
        return orderRepository.save(order);
    }

    private String getOrderRequestAsString(String clientReferenceCode) {
        return asJsonString(getOrderRequestDto(clientReferenceCode));
    }

    private String asJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Error while serializing to String", e);
            return "{}";
        }
    }

    private <T> T asObject(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (IOException e) {
            log.error("Error while deserializing from String", e);
            return null;
        }
    }

    private <T> T asObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error("Error while deserializing from String", e);
            return null;
        }
    }

}

