package ru.geekbrains.spring.market.model;

public enum OrderStatus {
    CREATED(1),
    WASP_PAID(2),
    COMPLETED(3);

    private final int status;

    OrderStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
