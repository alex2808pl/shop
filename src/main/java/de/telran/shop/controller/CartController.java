package de.telran.shop.controller;


import de.telran.shop.dto.CartDto;
import de.telran.shop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/cart")
public class CartController {
    private final CartService cartService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CartDto> getCart() {
        return cartService.getCart();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CartDto getCartById(@PathVariable Long id) {
        return cartService.getCartById(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartById(@PathVariable Long id) {
        cartService.deleteCartById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartDto insertCart(@RequestBody CartDto cartDto) {
        return cartService.insertCart(cartDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CartDto updateCart(@RequestBody CartDto cartDto) {
        return cartService.updateCart(cartDto);
    }

}
