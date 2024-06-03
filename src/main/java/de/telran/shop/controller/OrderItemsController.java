package de.telran.shop.controller;

import de.telran.shop.dto.OrderItemsDto;
import de.telran.shop.service.OrderItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/orderitems")
public class OrderItemsController {

    private final OrderItemsService orderItemsService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderItemsDto> getOrderItems() {
        return orderItemsService.getOrderItems();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderItemsDto getOrderItemsById(@PathVariable Long id) {
        return orderItemsService.getOrderItemsById(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderItemsById(@PathVariable Long id) {
        orderItemsService.deleteOrderItemsById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderItemsDto insertOrderItems(@RequestBody OrderItemsDto orderItemsDto) {
        return orderItemsService.insertOrderItems(orderItemsDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public OrderItemsDto updateOrderItems(@RequestBody OrderItemsDto orderItemsDto) {
        return orderItemsService.updateOrderItems(orderItemsDto);
    }
}
