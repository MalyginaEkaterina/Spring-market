package ru.geekbrains.spring.market.model;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {
    private Integer deliveryType;
    private Long deliveryDetails;
    private Double price;
    private Double deliveryPrice;
    private Long promoId;
    private List<FullBasketDto> items;
}
