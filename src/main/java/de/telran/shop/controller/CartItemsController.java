package de.telran.shop.controller;


import de.telran.shop.dto.CartItemsDto;
import de.telran.shop.exceptions.CartItemNotFoundException;
import de.telran.shop.exceptions.CartItemWrongValueException;
import de.telran.shop.service.CartItemsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/cartitems")
public class CartItemsController {
    private final CartItemsService cartItemsService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CartItemsDto> getCartItems() {
        return cartItemsService.getCartItems();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CartItemsDto getCartItemsById(@PathVariable Long id) {
        return cartItemsService.getCartItemsById(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartItemsById(@PathVariable Long id) {
        cartItemsService.deleteCartItemById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartItemsDto insertCartItems(@RequestBody CartItemsDto cartItemsDto) {
        return cartItemsService.insertCartItems(cartItemsDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CartItemsDto updateCartItems(@RequestBody CartItemsDto cartItemsDto) {
        return cartItemsService.updateCartItems(cartItemsDto);
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<ErrorMessage> errorMessage(CartItemNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }
    @ExceptionHandler(CartItemWrongValueException.class)
    public ResponseEntity<ErrorMessage> errorMessage(CartItemWrongValueException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }
}

