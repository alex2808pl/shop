package de.telran.shop.service;

import de.telran.shop.dto.OrderItemsDto;
import de.telran.shop.dto.OrdersDto;
import de.telran.shop.dto.UsersDto;
import de.telran.shop.entity.OrderItems;
import de.telran.shop.entity.Orders;
import de.telran.shop.entity.Users;
import de.telran.shop.repository.OrderItemsRepository;
import de.telran.shop.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemsService {

    private final OrdersRepository ordersRepository;
    private final OrderItemsRepository orderItemsRepository;

    public List<OrderItemsDto> getOrderItems() {
        List<OrderItems> orderItemsList = orderItemsRepository.findAll();
        List<OrderItemsDto> orderItemsDtoList =
                orderItemsList.stream()
                        .map(item -> OrderItemsDto.builder()
                                .orderItemId(item.getOrderItemId())
                                .productId(item.getProductId())
                                .quantity(item.getQuantity())
                                .priceAtPurchase(item.getPriceAtPurchase())
                                .orders(new OrdersDto(item.getOrders().getOrderId(),
                                        item.getOrders().getCreatedAt(),
                                        item.getOrders().getDeliveryAddress(),
                                        item.getOrders().getContactPhone(),
                                        item.getOrders().getDeliveryMethod(),
                                        item.getOrders().getStatus(),
                                        item.getOrders().getUpdatedAt(),
                                        (new UsersDto(item.getOrders().getUsers().getUserId(),
                                                item.getOrders().getUsers().getName(),
                                                item.getOrders().getUsers().getEmail(),
                                                item.getOrders().getUsers().getPhoneNumber(),
                                                item.getOrders().getUsers().getPasswordHash(),
                                                item.getOrders().getUsers().getRole()))))
                                .build())
                        .collect(Collectors.toList());
        return orderItemsDtoList;
    }


    public OrderItemsDto getOrderItemsById(Long id) {

        OrderItems orderItems = orderItemsRepository.findById(id).orElse(null);
        Orders orders = orderItems.getOrders();
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

        OrderItemsDto orderItemsDto = null;
        if (orderItems != null) {
            orderItemsDto = new OrderItemsDto(orderItems.getOrderItemId(),
                    orderItems.getProductId(),
                    orderItems.getQuantity(),
                    orderItems.getPriceAtPurchase(),
                    ordersDto);
        }
        return orderItemsDto;
    }

    public void deleteOrderItemsById(Long id) {
        OrderItems orderItems = orderItemsRepository.findById(id).orElse(null);
        if (orderItems != null) {
            orderItemsRepository.delete(orderItems);
        }
    }

    public OrderItemsDto insertOrderItems(OrderItemsDto orderItemsDto) {
        OrdersDto ordersDto = orderItemsDto.getOrders();
        Orders orders = null;
        Users users = null;

        if (ordersDto != null && ordersDto.getOrderId() != null) {
            orders = ordersRepository.findById(ordersDto.getOrderId()).orElse(null);
            users = orders.getUsers();
        }

        OrderItems orderItems = null;
        if (orders != null) {
            orderItems = new OrderItems(0,
                    orderItemsDto.getProductId(),
                    orderItemsDto.getQuantity(),
                    orderItemsDto.getPriceAtPurchase(),
                    orders);
        }
        orderItems = orderItemsRepository.save(orderItems);

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

        OrderItemsDto responceOrderItemsDto = new OrderItemsDto(orderItems.getOrderItemId(),
                orderItems.getProductId(),
                orderItems.getQuantity(),
                orderItems.getPriceAtPurchase(),
                responseOrdersDto);

        return responceOrderItemsDto;
    }

    public OrderItemsDto updateOrderItems(OrderItemsDto orderItemsDto) {
        if (orderItemsDto.getOrderItemId() <= 0) { // При редактировании такого быть не должно, нужно вывести пользователю ошибку
            return null;
        }

        OrderItems orderItems = orderItemsRepository.findById(orderItemsDto.getOrderItemId()).orElse(null);
        if (orderItems == null) {// Объект в БД не найден с таким orderId, нужно вывести пользователю ошибку
            return null;
        }

        OrdersDto ordersDto = orderItemsDto.getOrders();
        Orders orders = null;
        if(ordersDto != null && ordersDto.getOrderId() != 0){
            orders = orderItems.getOrders();
        }
        if (ordersDto.getOrderId() != orders.getOrderId()) {//номер заказа, введенный пользователем не совпадает с тем, который прописан в базе данных
            return null;
        }

        Users users = null;
        if (orders != null && orders.getOrderId() != 0) {
            users = orders.getUsers();
        }

        if (users != null) {
            orderItems.setProductId(orderItemsDto.getProductId());
            orderItems.setQuantity(orderItemsDto.getQuantity());
            orderItems.setPriceAtPurchase(orderItemsDto.getPriceAtPurchase());
            orderItems.setOrders(orders);
        }
        orderItems = orderItemsRepository.save(orderItems);

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

        OrderItemsDto responceOrderItemsDto = new OrderItemsDto(orderItems.getOrderItemId(),
                orderItems.getProductId(),
                orderItems.getQuantity(),
                orderItems.getPriceAtPurchase(),
                responseOrdersDto);

        return responceOrderItemsDto;
    }
}
