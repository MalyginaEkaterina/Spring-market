package ru.geekbrains.spring.market.exceptions;

public class ShopNotFoundException extends RuntimeException{
    public ShopNotFoundException(String msg) {
        super(msg);
    }
}
