package ru.geekbrains.spring.market.model;

import lombok.Data;

@Data
public class DeliveryPriceConditions {
    private double minTotalPrice;
    private double price;
}
