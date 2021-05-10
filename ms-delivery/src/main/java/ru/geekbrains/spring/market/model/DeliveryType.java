package ru.geekbrains.spring.market.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DeliveryType {
    COURIER(1),
    SHOP(2),
    PICK_UP_POINT(3);

    private final Integer id;

    DeliveryType(Integer type) {
        this.id = type;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return this.toString();
    }
}
