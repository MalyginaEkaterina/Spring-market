package ru.geekbrains.spring.market.exceptions;

public class PromoInvalidException extends RuntimeException{
    public PromoInvalidException(String msg) {
        super(msg);
    }
}
