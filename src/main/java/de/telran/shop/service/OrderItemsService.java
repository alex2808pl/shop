package de.telran.shop.service;

import de.telran.shop.config.MapperUtil;
import de.telran.shop.dto.CartDto;
import de.telran.shop.dto.OrderItemsDto;
import de.telran.shop.dto.OrdersDto;
import de.telran.shop.dto.UsersDto;
import de.telran.shop.entity.OrderItems;
import de.telran.shop.entity.Orders;
import de.telran.shop.entity.Users;
import de.telran.shop.mapper.Mappers;
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
    private final Mappers mappers;

    public List<OrderItemsDto> getOrderItems() {
        List<OrderItems> orderItemsList = orderItemsRepository.findAll();
        List<OrderItemsDto> orderItemsDtoList = MapperUtil.convertList(orderItemsList, mappers::convertToOrderItemsDto);

        return orderItemsDtoList;
    }


    public OrderItemsDto getOrderItemsById(Long id) {

        OrderItems orderItems = orderItemsRepository.findById(id).orElse(null);
        Orders orders = orderItems.getOrders();
        Users users = orders.getUsers();

        OrderItemsDto orderItemsDto = null;

        if (orderItems != null && orders != null && users != null) {
            orderItemsDto = mappers.convertToOrderItemsDto(orderItems);
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

        if (orderItemsDto.getOrders() == null && orderItemsDto.getOrders().getOrderId() == null) {
            return null;
        }
        OrderItems orderItems = mappers.convertToOrderItems(orderItemsDto);
        orderItems.setOrderItemId(0);

        Orders orders = ordersRepository.findById(orderItemsDto.getOrders().getOrderId()).orElse(null);
        orderItems.setOrders(orders);
        OrderItems savedOrderItems = orderItemsRepository.save(orderItems);

        return mappers.convertToOrderItemsDto(savedOrderItems);
    }


    public OrderItemsDto updateOrderItems(OrderItemsDto orderItemsDto) {
        if (orderItemsDto.getOrderItemId() <= 0) { // При редактировании такого быть не должно, нужно вывести пользователю ошибку
            return null;
        }

        OrderItems orderItems = orderItemsRepository.findById(orderItemsDto.getOrderItemId()).orElse(null);
        Orders orders = orderItems.getOrders();
        if (orderItems == null || orders == null) {// Объект в БД не найден с таким orderId, нужно вывести пользователю ошибку
            return null;
        }
        if (orderItemsDto.getOrderItemId() != orderItems.getOrderItemId()) {//номер orderItems, введенный пользователем не совпадает с тем, который прописан в базе данных
            return null;
        }

        orderItems = mappers.convertToOrderItems(orderItemsDto);
        OrderItems updatedOrderItems = orderItemsRepository.save(orderItems);
        OrderItemsDto responseOrderItemsDto = mappers.convertToOrderItemsDto(updatedOrderItems);

        return responseOrderItemsDto;
    }
}
