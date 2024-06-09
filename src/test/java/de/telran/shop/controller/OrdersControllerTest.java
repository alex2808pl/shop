package de.telran.shop.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.shop.dto.OrdersDto;
import de.telran.shop.dto.UsersDto;
import de.telran.shop.entity.enums.Status;
import de.telran.shop.service.OrdersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import java.util.List;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(OrdersController.class)
class OrdersControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrdersService ordersServiceMock;

    private OrdersDto ordersDto1;
    private OrdersDto ordersDto2;
    private OrdersDto ordersDto3;
    private OrdersDto ordersDto4;


    @BeforeEach
    void setUp() {
        ordersDto1 = OrdersDto.builder()
                .orderId(1L)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .deliveryAddress("Cherrystr, 19")
                .contactPhone("+491583265487")
                .deliveryMethod("Standard")
                .status(Status.ORDERED)
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .users(UsersDto.builder()
                        .userId(1L)
                        .build())
                .build();
    }

    @Test
    void testGetOrders() throws Exception {

        ordersDto2 = OrdersDto.builder()
                .orderId(2L)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .deliveryAddress("Oakstr, 37")
                .contactPhone("+4915321546213")
                .deliveryMethod("Standard")
                .status(Status.CONFIRMED)
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .users(UsersDto.builder()
                        .userId(1L)
                        .build())
                .build();

        when(ordersServiceMock.getOrders()).thenReturn(List.of(ordersDto1, ordersDto2));
        mockMvc.perform(get("/orders")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..orderId").exists())
                .andExpect(jsonPath("$..user").exists())
                .andExpect(jsonPath("$..createdAt").exists())
                .andExpect(jsonPath("$..status").exists());
    }

    @Test
    void testGetOrdersById() throws Exception {
        Long id = 1L;
        when(ordersServiceMock.getOrdersById(id)).thenReturn(ordersDto1);
        mockMvc.perform(get("/orders/{id}", id)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").exists())
                .andExpect(jsonPath("$.user").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.status").exists());
    }

    @Test
    void testDeleteOrdersById() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/orders/{id}", id)).andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.orderId").doesNotExist())
                .andExpect(jsonPath("$.user").doesNotExist())
                .andExpect(jsonPath("$.status").doesNotExist());
    }

    @Test
    void testInsertOrders() throws Exception {
        ordersDto3 = OrdersDto.builder()
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .deliveryAddress("Cherrystr, 19")
                .contactPhone("+491583265487")
                .deliveryMethod("Standard")
                .status(Status.ORDERED)
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .users(UsersDto.builder()
                        .userId(1L)
                        .build())
                .build();

        when(ordersServiceMock.insertOrders(any(OrdersDto.class))).thenReturn(ordersDto1);
        String requestBody = objectMapper.writeValueAsString(ordersDto3);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId").exists())
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.user").exists())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value("ORDERED"));
    }

    @Test
    void testUpdateOrders() throws Exception {
        ordersDto4 = OrdersDto.builder()
                .orderId(1L)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .deliveryAddress("Applestr, 19")
                .contactPhone("+491583265412")
                .deliveryMethod("Premium")
                .status(Status.ORDERED)
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .users(UsersDto.builder()
                        .userId(1L)
                        .build())
                .build();

        when(ordersServiceMock.updateOrders(any(OrdersDto.class))).thenReturn(ordersDto4);
        String requestBody = objectMapper.writeValueAsString(ordersDto1);

        mockMvc.perform(put("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").exists())
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.user").exists())
                .andExpect(jsonPath("$.deliveryAddress").value(ordersDto4.getDeliveryAddress()))
                .andExpect(jsonPath("$.contactPhone").value(ordersDto4.getContactPhone()));
    }
}
