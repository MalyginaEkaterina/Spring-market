package ru.geekbrains.spring.market.model;

public enum OrderStatus {
    CREATED(1, "Создан"),
    WASP_PAID(2, "Оплачен"),
    COMPLETED(3, "Выполнен");

    private final int status;
    private final String name;

    OrderStatus(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
