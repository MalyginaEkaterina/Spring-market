package ru.geekbrains.spring.market.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPriceRequestDto {
    private String deliveryType;
    private Float totalPrice;
}
