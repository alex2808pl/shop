package de.telran.shop.service;

import de.telran.shop.config.MapperUtil;
import de.telran.shop.dto.OrdersDto;
import de.telran.shop.dto.UsersDto;
import de.telran.shop.entity.Orders;
import de.telran.shop.entity.Users;
import de.telran.shop.mapper.Mappers;
import de.telran.shop.repository.OrdersRepository;
import de.telran.shop.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;
    private final Mappers mappers;

    public List<OrdersDto> getOrders() {
        List<Orders> ordersList = ordersRepository.findAll();
        List<OrdersDto> ordersDtoList = MapperUtil.convertList(ordersList, mappers::convertToOrdersDto);

        return ordersDtoList;
    }


    public OrdersDto getOrdersById(Long id) {
        Orders orders = ordersRepository.findById(id).orElse(null);
        Users users = orders.getUsers();
        OrdersDto ordersDto = null;

        if (orders != null && users != null) {
            ordersDto = mappers.convertToOrdersDto(orders);
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
        if (ordersDto.getUsers() == null && ordersDto.getUsers().getUserId() == null) {
            return null;
        }
        Orders orders = mappers.convertToOrders(ordersDto);
        orders.setOrderId(0);
        orders.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        Users users = usersRepository.findById(ordersDto.getUsers().getUserId()).orElse(null);
        orders.setUsers(users);
        Orders savedOrders = ordersRepository.save(orders);

        return mappers.convertToOrdersDto(savedOrders);
    }


    public OrdersDto updateOrders(OrdersDto ordersDto) {
        if (ordersDto.getOrderId() <= 0 || ordersDto.getUsers().getUserId() <= 0) { // При редактировании такого быть не должно, нужно вывести пользователю ошибку
            return null;
        }

        Orders orders = ordersRepository.findById(ordersDto.getOrderId()).orElse(null);
        Users users = orders.getUsers();
        if (orders == null || users == null) {// Объект в БД не найден с таким orderId, нужно вывести пользователю ошибку
            return null;
        }

        if (ordersDto.getOrderId() != orders.getOrderId()) {//номер orders, введенный пользователем не совпадает с тем, который прописан в базе данных
            return null;
        }
        orders = mappers.convertToOrders(ordersDto);
        orders.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        Orders updatedOrders = ordersRepository.save(orders);
        OrdersDto responseOrdersDto = mappers.convertToOrdersDto(updatedOrders);

        return responseOrdersDto;
    }
}
