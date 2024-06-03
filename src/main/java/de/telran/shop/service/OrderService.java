package de.telran.shop.service;

import de.telran.shop.dto.OrdersDto;
import de.telran.shop.dto.UsersDto;
import de.telran.shop.entity.Orders;
import de.telran.shop.entity.Users;
import de.telran.shop.repository.OrdersRepository;
import de.telran.shop.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;

    public List<OrdersDto> getOrders() {
        List<Orders> ordersList = ordersRepository.findAll();
        List<OrdersDto> ordersDtoList =
                ordersList.stream()
                        .map(order -> OrdersDto.builder()
                                .orderId(order.getOrderId())
                                .createdAt(order.getCreatedAt())
                                .deliveryAddress(order.getDeliveryAddress())
                                .contactPhone(order.getContactPhone())
                                .deliveryMethod(order.getDeliveryMethod())
                                .status(order.getStatus())
                                .updatedAt(order.getUpdatedAt())
                                .users(new UsersDto(order.getUsers().getUserId(),
                                        order.getUsers().getName(),
                                        order.getUsers().getEmail(),
                                        order.getUsers().getPhoneNumber(),
                                        order.getUsers().getPasswordHash(),
                                        order.getUsers().getRole()))
                                .build())
                        .collect(Collectors.toList());
        return ordersDtoList;
    }


    public OrdersDto getOrdersById(Long id) {

        Orders orders = ordersRepository.findById(id).orElse(null);
        Users users = orders.getUsers();
        UsersDto usersDto = null;
        if (users != null) {
            usersDto = new UsersDto(users.getUserId(),
                    users.getName(),
                    users.getEmail(),
                    users.getPhoneNumber(),
                    users.getPasswordHash(),
                    users.getRole());
        }
        OrdersDto ordersDto = null;
        if (orders != null) {
            ordersDto = new OrdersDto(orders.getOrderId(),
                    orders.getCreatedAt(),
                    orders.getDeliveryAddress(),
                    orders.getContactPhone(),
                    orders.getDeliveryMethod(),
                    orders.getStatus(),
                    orders.getUpdatedAt(),
                    usersDto);
        }
        return ordersDto;
    }

    public void deleteOrdersById(Long id) {
        Orders orders = ordersRepository.findById(id).orElse(null);
        if (orders != null) {
            ordersRepository.delete(orders);
        }
    }

    public OrdersDto insertOrders(OrdersDto ordersDto) {

        UsersDto usersDto = ordersDto.getUsers();
        Users users = null;
        if (usersDto != null && usersDto.getUserId() != null) {
            users = usersRepository.findById(usersDto.getUserId()).orElse(null);
        }

        Orders orders = null;
        if (users != null) {
            orders = new Orders(0,
                    ordersDto.getCreatedAt(),
                    ordersDto.getDeliveryAddress(),
                    ordersDto.getContactPhone(),
                    ordersDto.getDeliveryMethod(),
                    ordersDto.getStatus(),
                    ordersDto.getUpdatedAt(),
                    users,
                    null);
        }
        orders = ordersRepository.save(orders);

        UsersDto responceUsersDto = new UsersDto(users.getUserId(),
                users.getName(),
                users.getEmail(),
                users.getPhoneNumber(),
                users.getPasswordHash(),
                users.getRole());


        OrdersDto responseOrdersDto = new OrdersDto(orders.getOrderId(),
                orders.getCreatedAt(),
                orders.getDeliveryAddress(),
                orders.getContactPhone(),
                orders.getDeliveryMethod(),
                orders.getStatus(),
                orders.getUpdatedAt(),
                responceUsersDto);

        return responseOrdersDto;
    }

    public OrdersDto updateOrders(OrdersDto ordersDto) {
        if (ordersDto.getOrderId() <= 0) { // При редактировании такого быть не должно, нужно вывести пользователю ошибку
            return null;
        }

        Orders orders = ordersRepository.findById(ordersDto.getOrderId()).orElse(null);
        if (orders == null) {// Объект в БД не найден с таким orderId, нужно вывести пользователю ошибку
            return null;
        }

        UsersDto usersDto = ordersDto.getUsers();
        Users users = null;
        if(usersDto!=null && usersDto.getUserId()!=null){
            users = orders.getUsers();
        }
        if (usersDto.getUserId() != users.getUserId()) {//номер users, введенный пользователем не совпадает с тем, который прописан в базе данных
            return null;
        }

        if (users != null && users.getUserId() != 0) {
            orders.setCreatedAt(ordersDto.getCreatedAt());
            orders.setDeliveryAddress(ordersDto.getDeliveryAddress());
            orders.setContactPhone(ordersDto.getContactPhone());
            orders.setDeliveryMethod(ordersDto.getDeliveryMethod());
            orders.setStatus(ordersDto.getStatus());
            orders.setUpdatedAt(ordersDto.getUpdatedAt());
            orders.setUsers(users);
        }
        orders = ordersRepository.save(orders);

        UsersDto responceUsersDto = new UsersDto(users.getUserId(),
                users.getName(),
                users.getEmail(),
                users.getPhoneNumber(),
                users.getPasswordHash(),
                users.getRole());

        OrdersDto responseOrdersDto = new OrdersDto(orders.getOrderId(),
                orders.getCreatedAt(),
                orders.getDeliveryAddress(),
                orders.getContactPhone(),
                orders.getDeliveryMethod(),
                orders.getStatus(),
                orders.getUpdatedAt(),
                responceUsersDto);

        return responseOrdersDto;
    }
}
