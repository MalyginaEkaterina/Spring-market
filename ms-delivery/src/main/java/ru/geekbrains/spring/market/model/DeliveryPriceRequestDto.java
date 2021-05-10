package ru.geekbrains.spring.market.model;

import lombok.Data;

@Data
public class DeliveryPriceRequestDto {
    private String deliveryType;
    private Float totalPrice;
}
