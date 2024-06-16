package de.telran.shop.exceptions;

public class CartItemWrongValueException extends RuntimeException{
    public CartItemWrongValueException(String message) {
        super(message);
    }
}
