package ru.geekbrains.spring.market.exceptions;

public class IncorrectParamException extends RuntimeException{
    public IncorrectParamException(String msg) {
        super(msg);
    }
}
