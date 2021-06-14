package ru.geekbrains.spring.market.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.market.model.DeliveryInfoResponseDto;
import ru.geekbrains.spring.market.model.PickUpPoint;
import ru.geekbrains.spring.market.model.PickUpPointDto;
import ru.geekbrains.spring.market.services.DeliveryService;

@Service
public class PickUpPointDeliveryStrategy implements DeliveryStrategy {
    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DeliveryInfoResponseDto getDeliveryInfo(Long deliveryDetails) {
        PickUpPoint pickUpPoint = deliveryService.getPickUpPoint(deliveryDetails);
        return DeliveryInfoResponseDto.builder()
                .pickUpPoint(modelMapper.map(pickUpPoint, PickUpPointDto.class))
                .build();
    }
}
