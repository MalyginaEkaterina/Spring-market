package ru.geekbrains.spring.market.exceptions;

import ru.geekbrains.spring.market.model.ProductReserveResponse;

public class NotEnoughProductsException extends RuntimeException{

    private ProductReserveResponse productReserveResponse;

    public NotEnoughProductsException(String msg, ProductReserveResponse productReserveResponse) {
        super(msg);
        this.productReserveResponse = productReserveResponse;
    }

    public ProductReserveResponse getProductReserveResponse() {
        return productReserveResponse;
    }
}