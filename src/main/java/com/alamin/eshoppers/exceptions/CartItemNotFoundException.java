package com.alamin.eshoppers.exceptions;

public class CartItemNotFoundException extends RuntimeException {
    public CartItemNotFoundException() {

    }
    public CartItemNotFoundException(String message) {
        super(message);
    }
}
