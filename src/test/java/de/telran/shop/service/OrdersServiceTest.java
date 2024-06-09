package de.telran.shop.service;

import de.telran.shop.dto.OrdersDto;
import de.telran.shop.dto.UsersDto;
import de.telran.shop.entity.*;
import de.telran.shop.entity.enums.Role;
import de.telran.shop.entity.enums.Status;
import de.telran.shop.mapper.Mappers;
import de.telran.shop.repository.OrdersRepository;
import de.telran.shop.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrdersServiceTest {

    @Mock
    private OrdersRepository ordersRepositoryMock;

    @Mock
    private UsersRepository usersRepositoryMock;

    @Mock
    private Mappers mappersMock;

    @InjectMocks
    private OrdersService ordersServiceMock;

    private OrdersDto ordersDto1;
    private OrdersDto ordersDto2;
    private OrdersDto ordersDto3;
    private OrdersDto ordersDto4;

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
    void testGetOrders() {

        users2 = new Users(
                1L,
                "Jane Green",
                "janegreen@example.com",
                "+49153215694",
                "PasswordTwo",
                Role.CLIENT,
                new Cart(),
                new HashSet<Favorites>(),
                new HashSet<Orders>());

        orders2 = new Orders(
                1L,
                Timestamp.valueOf(LocalDateTime.now()),
                "Oakstr, 36",
                "+4915634857",
                "Standard",
                Status.PAID,
                Timestamp.valueOf(LocalDateTime.now()),
                users2,
                new HashSet<OrderItems>());

        ordersDto2 = OrdersDto.builder()
                .orderId(1L)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .deliveryAddress("Oakstr, 36")
                .contactPhone("+4915634857")
                .deliveryMethod("Standard")
                .status(Status.PAID)
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .users(UsersDto.builder()
                        .userId(1L)
                        .build())
                .build();

        when(ordersRepositoryMock.findAll()).thenReturn(List.of(orders1, orders2));
        when(mappersMock.convertToOrdersDto(orders1)).thenReturn(ordersDto1);
        when(mappersMock.convertToOrdersDto(orders2)).thenReturn(ordersDto2);

        List<OrdersDto> actualOrdersDtoList = ordersServiceMock.getOrders();

        assertTrue(actualOrdersDtoList.size() > 0);
        verify(mappersMock, times(2)).convertToOrdersDto(any(Orders.class));
        verify(ordersRepositoryMock, times(1)).findAll();
        assertEquals(orders1.getOrderId(), actualOrdersDtoList.get(0).getOrderId());
        assertEquals(orders1.getUsers().getUserId(), actualOrdersDtoList.get(0).getUsers().getUserId());
    }

    @Test
    void testGetOrdersById() {

        Long id = 1l;
        when(ordersRepositoryMock.findById(id)).thenReturn(Optional.of(orders1));
        when(mappersMock.convertToOrdersDto(orders1)).thenReturn(ordersDto1);

        OrdersDto actualOrdersDto = ordersServiceMock.getOrdersById(id);

        assertNotNull(ordersDto1);
        verify(mappersMock, times(1)).convertToOrdersDto(any(Orders.class));
        verify(ordersRepositoryMock, times(1)).findById(id);
        assertEquals(orders1.getOrderId(), actualOrdersDto.getOrderId());
        assertEquals(orders1.getUsers().getUserId(), actualOrdersDto.getUsers().getUserId());
    }

    @Test
    void tesDeleteOrdersById() {

        Long id = 1l;
        when(ordersRepositoryMock.findById(id)).thenReturn(Optional.of(orders1));

        ordersServiceMock.deleteOrdersById(id);

        assertNull(ordersServiceMock.getOrdersById(id));
        verify(ordersRepositoryMock, times(1)).delete(orders1);
    }

    @Test
    void testInsertOrders() {

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
                0L,
                Timestamp.valueOf(LocalDateTime.now()),
                "Cherrystr, 19",
                "+491583265487",
                "Standard",
                Status.ORDERED,
                Timestamp.valueOf(LocalDateTime.now()),
                users3,
                new HashSet<OrderItems>());

        when(mappersMock.convertToOrders(ordersDto3)).thenReturn(orders3);
        when(ordersRepositoryMock.save(any(Orders.class))).thenReturn(orders1);
        when(mappersMock.convertToOrdersDto(orders1)).thenReturn(ordersDto1);

        OrdersDto actualOrdersDto = ordersServiceMock.insertOrders(ordersDto3);

        assertNotNull(actualOrdersDto);
        assertNotNull(actualOrdersDto.getOrderId());
        assertNotNull(actualOrdersDto.getUsers().getUserId());


        verify(mappersMock, times(1)).convertToOrders(any(OrdersDto.class));
        verify(mappersMock, times(1)).convertToOrdersDto(any(Orders.class));
        verify(ordersRepositoryMock, times(1)).save(any(Orders.class));

        assertEquals(ordersDto3.getUsers().getUserId(), actualOrdersDto.getUsers().getUserId());
        assertEquals(ordersDto3.getStatus(), actualOrdersDto.getStatus());
    }

    @Test
    void testUpdateOrders() {

        ordersDto4 = OrdersDto.builder()
                .orderId(1L)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .deliveryAddress("Cherrystr, 19")
                .contactPhone("+491583265487")
                .deliveryMethod("Standard")
                .status(Status.PAID)
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .users(UsersDto.builder()
                        .userId(1L)
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
                0L,
                Timestamp.valueOf(LocalDateTime.now()),
                "Cherrystr, 19",
                "+491583265487",
                "Standard",
                Status.PAID,
                Timestamp.valueOf(LocalDateTime.now()),
                users4,
                new HashSet<OrderItems>());

        when(ordersRepositoryMock.findById(ordersDto4.getOrderId())).thenReturn(Optional.of(orders1));
        when(mappersMock.convertToOrders(ordersDto4)).thenReturn(orders4);
        when(ordersRepositoryMock.save(any(Orders.class))).thenReturn(orders4);
        when(mappersMock.convertToOrdersDto(orders4)).thenReturn(ordersDto4);

        OrdersDto actualOrdersDto = ordersServiceMock.updateOrders(ordersDto4);

        assertNotNull(actualOrdersDto);
        assertNotNull(actualOrdersDto.getOrderId());

        verify(mappersMock, times(1)).convertToOrders(any(OrdersDto.class));
        verify(ordersRepositoryMock, times(1)).save(any(Orders.class));
        verify(mappersMock, times(1)).convertToOrdersDto(any(Orders.class));

        assertEquals(ordersDto4.getOrderId(), actualOrdersDto.getOrderId());
        assertEquals(ordersDto4.getUsers().getUserId(), actualOrdersDto.getUsers().getUserId());
        assertEquals(ordersDto4.getStatus(), actualOrdersDto.getStatus());
    }
}