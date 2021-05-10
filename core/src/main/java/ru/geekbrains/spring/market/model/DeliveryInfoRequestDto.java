package ru.geekbrains.spring.market.model;

import lombok.Data;

@Data
public class DeliveryInfoRequestDto {
    private Integer deliveryType;
    private Long deliveryDetails;
}
