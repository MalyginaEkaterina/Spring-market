package ru.geekbrains.spring.market.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.market.AuthClient;
import ru.geekbrains.spring.market.model.DeliveryInfoResponseDto;
import ru.geekbrains.spring.market.model.UserDeliveryAddressDto;

@Service
public class CourierDeliveryStrategy implements DeliveryStrategy{

    @Autowired
    private AuthClient authClient;

    @Override
    public DeliveryInfoResponseDto getDeliveryInfo(Long deliveryDetails) {
        UserDeliveryAddressDto u = authClient.getUserAddress(deliveryDetails);
        return DeliveryInfoResponseDto.builder()
                .userDeliveryAddress(u)
                .build();
    }
}
