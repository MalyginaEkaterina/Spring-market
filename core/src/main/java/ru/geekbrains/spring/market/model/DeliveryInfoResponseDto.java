package ru.geekbrains.spring.market.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryInfoResponseDto {
    private ShopDto shop;
    private PickUpPointDto pickUpPoint;
    private UserDeliveryAddressDto userDeliveryAddress;
}
