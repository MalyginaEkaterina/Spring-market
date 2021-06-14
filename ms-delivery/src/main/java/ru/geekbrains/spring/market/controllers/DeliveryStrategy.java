package ru.geekbrains.spring.market.controllers;

import ru.geekbrains.spring.market.model.DeliveryInfoResponseDto;

public interface DeliveryStrategy {
    DeliveryInfoResponseDto getDeliveryInfo(Long deliveryDetails);
}
