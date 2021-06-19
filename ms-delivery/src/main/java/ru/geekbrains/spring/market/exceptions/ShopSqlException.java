package ru.geekbrains.spring.market.exceptions;

public class ShopSqlException extends RuntimeException{
    public ShopSqlException(String msg) {
        super(msg);
    }
}
