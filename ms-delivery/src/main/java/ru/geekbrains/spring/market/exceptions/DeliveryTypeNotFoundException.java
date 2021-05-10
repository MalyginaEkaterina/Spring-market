package ru.geekbrains.spring.market.exceptions;

public class DeliveryTypeNotFoundException extends RuntimeException{
    public DeliveryTypeNotFoundException(String msg) {
        super(msg);
    }
}
