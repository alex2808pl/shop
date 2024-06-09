package de.telran.shop.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.shop.dto.OrderItemsDto;
import de.telran.shop.dto.OrdersDto;
import de.telran.shop.dto.UsersDto;
import de.telran.shop.service.OrderItemsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(OrderItemsController.class)
public class OrderItemsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderItemsService orderItemsServiceMock;

    private OrderItemsDto orderItemsDto1;
    private OrderItemsDto orderItemsDto2;
    private OrderItemsDto orderItemsDto3;
    private OrderItemsDto orderItemsDto4;

    @BeforeEach
    void setUp() {
        orderItemsDto1 = OrderItemsDto.builder()
                .orderItemId(1L)
                .productId(1L)
                .quantity(500)
                .priceAtPurchase(BigDecimal.valueOf(100))
                .orders(OrdersDto.builder()
                        .orderId(1L)
                        .users(UsersDto.builder()
                                .userId(1L)
                                .build())
                        .build())
                .build();
    }

    @Test
    void testGetOrderItems() throws Exception {

        orderItemsDto2 = OrderItemsDto.builder()
                .orderItemId(2L)
                .productId(2L)
                .quantity(600)
                .priceAtPurchase(BigDecimal.valueOf(200))
                .orders(OrdersDto.builder()
                        .orderId(2L)
                        .users(UsersDto.builder()
                                .userId(2L)
                                .build())
                        .build())
                .build();

        when(orderItemsServiceMock.getOrderItems()).thenReturn(List.of(orderItemsDto1, orderItemsDto2));
        mockMvc.perform(get("/orderitems")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..orderItemId").exists())
                .andExpect(jsonPath("$..orderId").exists())
                .andExpect(jsonPath("$..userId").exists());
    }

    @Test
    void testGetOrderItemsById() throws Exception {
        Long id = 1L;
        when(orderItemsServiceMock.getOrderItemsById(id)).thenReturn(orderItemsDto1);
        mockMvc.perform(get("/orderitems/{id}", id)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderItemId").exists())
                .andExpect(jsonPath("$..orderId").exists())
                .andExpect(jsonPath("$..userId").exists());
    }

    @Test
    void testDeleteOrderItemsById() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/orderitems/{id}", id)).andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.orderItemId").doesNotExist())
                .andExpect(jsonPath("$.quantity").doesNotExist())
                .andExpect(jsonPath("$.priceAtPurchase").doesNotExist());
    }

    @Test
    void testInsertOrderItems() throws Exception {
        orderItemsDto3 = OrderItemsDto.builder()
                .productId(1L)
                .quantity(800)
                .priceAtPurchase(BigDecimal.valueOf(400))
                .orders(OrdersDto.builder()
                        .orderId(1L)
                        .users(UsersDto.builder()
                                .userId(1L)
                                .build())
                        .build())
                .build();

        when(orderItemsServiceMock.insertOrderItems(any(OrderItemsDto.class))).thenReturn(orderItemsDto1);
        String requestBody = objectMapper.writeValueAsString(orderItemsDto3);

        mockMvc.perform(post("/orderitems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderItemId").exists())
                .andExpect(jsonPath("$..orderId").exists())
                .andExpect(jsonPath("$..userId").exists());
    }

    @Test
    void testUpdateOrderItems() throws Exception {
        orderItemsDto4 = OrderItemsDto.builder()
                .orderItemId(2L)
                .productId(3L)
                .quantity(800)
                .priceAtPurchase(BigDecimal.valueOf(400))
                .orders(OrdersDto.builder()
                        .orderId(1L)
                        .users(UsersDto.builder()
                                .userId(1L)
                                .build())
                        .build())
                .build();

        when(orderItemsServiceMock.updateOrderItems(any(OrderItemsDto.class))).thenReturn(orderItemsDto4);
        String requestBody = objectMapper.writeValueAsString(orderItemsDto1);

        mockMvc.perform(put("/orderitems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderItemId").exists())
                .andExpect(jsonPath("$..orderId").exists())
                .andExpect(jsonPath("$..userId").exists())
                .andExpect(jsonPath("$.quantity").value(orderItemsDto4.getQuantity()))
                .andExpect(jsonPath("$.priceAtPurchase").value(orderItemsDto4.getPriceAtPurchase()));
    }
}






