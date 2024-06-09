package de.telran.shop.controller;

import de.telran.shop.dto.OrdersDto;
import de.telran.shop.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/orders")
public class OrdersController {

    private final OrdersService ordersService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrdersDto> getOrders() {
        return ordersService.getOrders();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrdersDto getOrdersById(@PathVariable Long id) {
        return ordersService.getOrdersById(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrdersById(@PathVariable Long id) {
        ordersService.deleteOrdersById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdersDto insertOrders(@RequestBody OrdersDto ordersDto) {
        return ordersService.insertOrders(ordersDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public OrdersDto updateOrders(@RequestBody OrdersDto ordersDto) {
        return ordersService.updateOrders(ordersDto);
    }
}
