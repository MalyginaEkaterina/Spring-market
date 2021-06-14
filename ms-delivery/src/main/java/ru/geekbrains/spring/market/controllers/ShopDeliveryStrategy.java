package ru.geekbrains.spring.market.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.market.model.DeliveryInfoResponseDto;
import ru.geekbrains.spring.market.model.Shop;
import ru.geekbrains.spring.market.model.ShopDto;
import ru.geekbrains.spring.market.services.CachingShopService;

@Service
public class ShopDeliveryStrategy implements DeliveryStrategy{
    @Autowired
    private CachingShopService shopService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DeliveryInfoResponseDto getDeliveryInfo(Long deliveryDetails) {
        Shop shop = shopService.getShop(deliveryDetails);
        return DeliveryInfoResponseDto.builder()
                .shop(modelMapper.map(shop, ShopDto.class))
                .build();
    }
}
