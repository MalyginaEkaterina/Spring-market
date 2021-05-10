package ru.geekbrains.spring.market.model;

import lombok.Data;

@Data
public class DeliveryInfoResponseDto {
    private ShopDto shop;
    private PickUpPointDto pickUpPoint;
    private UserDeliveryAddressDto userDeliveryAddress;
}
