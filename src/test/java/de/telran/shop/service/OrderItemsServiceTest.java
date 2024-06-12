package de.telran.shop.service;

import de.telran.shop.dto.OrderItemsDto;
import de.telran.shop.dto.OrdersDto;
import de.telran.shop.dto.UsersDto;
import de.telran.shop.entity.*;
import de.telran.shop.entity.enums.Role;
import de.telran.shop.entity.enums.Status;
import de.telran.shop.mapper.Mappers;
import de.telran.shop.repository.OrderItemsRepository;
import de.telran.shop.repository.OrdersRepository;
import de.telran.shop.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderItemsServiceTest {

    @Mock
    private OrderItemsRepository orderItemsRepositoryMock;

    @Mock
    private OrdersRepository ordersRepositoryMock;

    @Mock
    private UsersRepository usersRepositoryMock;

    @Mock
    private Mappers mappersMock;

    @InjectMocks
    private OrderItemsService orderItemsServiceMock;

    private OrderItemsDto orderItemsDto1;
    private OrderItemsDto orderItemsDto2;
    private OrderItemsDto orderItemsDto3;
    private OrderItemsDto orderItemsDto4;

    private OrderItems orderItems1;
    private OrderItems orderItems2;
    private OrderItems orderItems3;
    private OrderItems orderItems4;

    private Orders orders1;
    private Orders orders2;
    private Orders orders3;
    private Orders orders4;

    private Users users1;
    private Users users2;
    private Users users3;
    private Users users4;

    @BeforeEach
    void setUp() {

        users1 = new Users(
                1L,
                "Mary Anderson",
                "maryanderson@example.com",
                "+491583265487",
                "PasswordOne",
                Role.CLIENT,
                new Cart(),
                new HashSet<Favorites>(),
                new HashSet<Orders>());

        orders1 = new Orders(
                1L,
                Timestamp.valueOf(LocalDateTime.now()),
                "Cherrystr, 19",
                "+491583265487",
                "Standard",
                Status.ORDERED,
                Timestamp.valueOf(LocalDateTime.now()),
                users1,
                new HashSet<OrderItems>());

        orderItems1 = new OrderItems(
                1L,
                1L,
                400,
                new BigDecimal("200.00"),
                orders1);

        orderItemsDto1 = OrderItemsDto.builder()
                .orderItemId(1L)
                .productId(1L)
                .quantity(400)
                .priceAtPurchase(new BigDecimal("200.00"))
                .orders(OrdersDto.builder()
                        .orderId(1L)
                        .users(UsersDto.builder()
                                .userId(1L)
                                .build())
                        .build())
                .build();
    }

    @Test
    void testGetOrderItems() {
        users2 = new Users(
                1L,
                "Mary Anderson",
                "maryanderson@example.com",
                "+491583265487",
                "PasswordOne",
                Role.CLIENT,
                new Cart(),
                new HashSet<Favorites>(),
                new HashSet<Orders>());

        orders2 = new Orders(
                1L,
                Timestamp.valueOf(LocalDateTime.now()),
                "Cherrystr, 19",
                "+491583265487",
                "Standard",
                Status.ORDERED,
                Timestamp.valueOf(LocalDateTime.now()),
                users1,
                new HashSet<OrderItems>());

        orderItems2 = new OrderItems(
                2L,
                1L,
                300,
                new BigDecimal("500.00"),
                orders1);

        orderItemsDto2 = OrderItemsDto.builder()
                .orderItemId(2L)
                .productId(1L)
                .quantity(300)
                .priceAtPurchase(new BigDecimal("500.00"))
                .orders(OrdersDto.builder()
                        .orderId(1L)
                        .users(UsersDto.builder()
                                .userId(1L)
                                .build())
                        .build())
                .build();

        when(orderItemsRepositoryMock.findAll()).thenReturn(List.of(orderItems1, orderItems2));
        when(mappersMock.convertToOrderItemsDto(orderItems1)).thenReturn(orderItemsDto1);
        when(mappersMock.convertToOrderItemsDto(orderItems2)).thenReturn(orderItemsDto2);

        List<OrderItemsDto> actualOrderItemsDtoList = orderItemsServiceMock.getOrderItems();

        assertTrue(actualOrderItemsDtoList.size() > 0);
        verify(mappersMock, times(2)).convertToOrderItemsDto(any(OrderItems.class));
        verify(orderItemsRepositoryMock, times(1)).findAll();
        assertEquals(orderItems1.getOrderItemId(), actualOrderItemsDtoList.get(0).getOrderItemId());
        assertEquals(orderItems1.getOrders().getOrderId(), actualOrderItemsDtoList.get(0).getOrders().getOrderId());
        assertEquals(orderItems1.getOrders().getUsers().getUserId(), actualOrderItemsDtoList.get(0).getOrders().getUsers().getUserId());
    }

    @Test
    void testGetOrderItemsById() {

        Long id = 1l;
        when(orderItemsRepositoryMock.findById(id)).thenReturn(Optional.of(orderItems1));
        when(mappersMock.convertToOrderItemsDto(orderItems1)).thenReturn(orderItemsDto1);

        OrderItemsDto actualOrderItemsDto = orderItemsServiceMock.getOrderItemsById(id);

        assertNotNull(orderItemsDto1);
        verify(mappersMock, times(1)).convertToOrderItemsDto(any(OrderItems.class));
        verify(orderItemsRepositoryMock, times(1)).findById(id);
        assertEquals(orderItems1.getOrderItemId(), actualOrderItemsDto.getOrderItemId());
        assertEquals(orderItems1.getOrders().getOrderId(), actualOrderItemsDto.getOrders().getOrderId());
        assertEquals(orderItems1.getOrders().getUsers().getUserId(), actualOrderItemsDto.getOrders().getUsers().getUserId());
    }

    @Test
    void testDeleteOrderItemsById() {
        Long id = 1l;
        when(orderItemsRepositoryMock.findById(id)).thenReturn(Optional.of(orderItems1));

        orderItemsServiceMock.deleteOrderItemsById(id);

        assertNull(orderItemsServiceMock.getOrderItemsById(id));
        verify(orderItemsRepositoryMock, times(1)).delete(orderItems1);
    }

    @Test
    void testInsertOrderItems() {

        orderItemsDto3 = OrderItemsDto.builder()
                .productId(1L)
                .quantity(400)
                .priceAtPurchase(new BigDecimal("200.00"))
                .orders(OrdersDto.builder()
                        .orderId(1L)
                        .users(UsersDto.builder()
                                .userId(1L)
                                .build())
                        .build())
                .build();


        users3 = new Users(
                1L,
                "Mary Anderson",
                "maryanderson@example.com",
                "+491583265487",
                "PasswordOne",
                Role.CLIENT,
                new Cart(),
                new HashSet<Favorites>(),
                new HashSet<Orders>());

        orders3 = new Orders(
                1L,
                Timestamp.valueOf(LocalDateTime.now()),
                "Cherrystr, 19",
                "+491583265487",
                "Standard",
                Status.ORDERED,
                Timestamp.valueOf(LocalDateTime.now()),
                users3,
                new HashSet<OrderItems>());

        orderItems3 = new OrderItems(
                0L,
                1L,
                400,
                new BigDecimal("200.00"),
                orders1);

        when(ordersRepositoryMock.findById(orderItemsDto3.getOrders().getOrderId())).thenReturn(Optional.of(orders3));
        when(mappersMock.convertToOrderItems(orderItemsDto3)).thenReturn(orderItems3);
        when(orderItemsRepositoryMock.save(any(OrderItems.class))).thenReturn(orderItems1);
        when(mappersMock.convertToOrderItemsDto(orderItems1)).thenReturn(orderItemsDto1);

        OrderItemsDto actualOrderItemsDto = orderItemsServiceMock.insertOrderItems(orderItemsDto3);

        assertNotNull(actualOrderItemsDto);
        assertNotNull(actualOrderItemsDto.getOrderItemId());
        assertNotNull(actualOrderItemsDto.getOrders().getOrderId());
        assertNotNull(actualOrderItemsDto.getOrders().getUsers().getUserId());

        verify(mappersMock, times(1)).convertToOrderItems(any(OrderItemsDto.class));
        verify(mappersMock, times(1)).convertToOrderItemsDto(any(OrderItems.class));
        verify(orderItemsRepositoryMock, times(1)).save(any(OrderItems.class));

        assertEquals(orderItemsDto3.getOrders().getOrderId(), actualOrderItemsDto.getOrders().getOrderId());
        assertEquals(orderItemsDto3.getOrders().getUsers().getUserId(), actualOrderItemsDto.getOrders().getUsers().getUserId());
        assertEquals(orderItemsDto3.getProductId(), actualOrderItemsDto.getProductId());
    }

    @Test
    void testUpdateOrderItems() {

        orderItemsDto4 = OrderItemsDto.builder()
                .orderItemId(1L)
                .productId(1L)
                .quantity(200)
                .priceAtPurchase(new BigDecimal("200.00"))
                .orders(OrdersDto.builder()
                        .orderId(1L)
                        .users(UsersDto.builder()
                                .userId(1L)
                                .build())
                        .build())
                .build();

        users4 = new Users(
                1L,
                "Mary Anderson",
                "maryanderson@example.com",
                "+491583265487",
                "PasswordOne",
                Role.CLIENT,
                new Cart(),
                new HashSet<Favorites>(),
                new HashSet<Orders>());

        orders4 = new Orders(
                1L,
                Timestamp.valueOf(LocalDateTime.now()),
                "Cherrystr, 19",
                "+491583265487",
                "Standard",
                Status.PAID,
                Timestamp.valueOf(LocalDateTime.now()),
                users4,
                new HashSet<OrderItems>());

        orderItems4 = new OrderItems(
                1L,
                1L,
                200,
                new BigDecimal("200.00"),
                orders1);

        when(orderItemsRepositoryMock.findById(orderItemsDto4.getOrderItemId())).thenReturn(Optional.of(orderItems1));
        when(mappersMock.convertToOrderItems(orderItemsDto4)).thenReturn(orderItems4);
        when(orderItemsRepositoryMock.save(any(OrderItems.class))).thenReturn(orderItems4);
        when(mappersMock.convertToOrderItemsDto(orderItems4)).thenReturn(orderItemsDto4);

        OrderItemsDto actualOrderItemsDto = orderItemsServiceMock.updateOrderItems(orderItemsDto4);

        assertNotNull(actualOrderItemsDto);
        assertNotNull(actualOrderItemsDto.getOrderItemId());

        verify(mappersMock, times(1)).convertToOrderItems(any(OrderItemsDto.class));
        verify(orderItemsRepositoryMock, times(1)).save(any(OrderItems.class));
        verify(mappersMock, times(1)).convertToOrderItemsDto(any(OrderItems.class));

        assertEquals(orderItemsDto4.getOrderItemId(), actualOrderItemsDto.getOrderItemId());
        assertEquals(orderItemsDto4.getOrders().getOrderId(), actualOrderItemsDto.getOrders().getOrderId());
        assertEquals(orderItemsDto4.getOrders().getUsers().getUserId(), actualOrderItemsDto.getOrders().getUsers().getUserId());
        assertEquals(orderItemsDto4.getProductId(), actualOrderItemsDto.getProductId());
    }
}