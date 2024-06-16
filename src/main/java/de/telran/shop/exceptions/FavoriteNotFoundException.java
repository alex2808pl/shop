package de.telran.shop.exceptions;

public class FavoriteNotFoundException extends RuntimeException{
    public FavoriteNotFoundException(String message) {
        super(message);
    }
}
