package ru.geekbrains.spring.market.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.market.DeliveryPrice;
import ru.geekbrains.spring.market.exceptions.ShopNotFoundException;
import ru.geekbrains.spring.market.model.*;
import ru.geekbrains.spring.market.repositories.DeliveryRedisRepository;
import ru.geekbrains.spring.market.repositories.PickUpPointRepository;

import java.util.List;

@Service
public class DeliveryService {
    @Autowired
    private PickUpPointRepository pickUpPointRepository;

    @Autowired
    private DeliveryRedisRepository deliveryRedisRepository;

    @Autowired
    private DeliveryPrice deliveryPrice;

//    public List<Shop> getAllShops() {
//        List<Shop> shops = deliveryRedisRepository.getShops();
//        if (shops != null) {
//            return shops;
//        }
//        shops = shopRepository.findAll();
//        if (!shops.isEmpty()) {
//            deliveryRedisRepository.putShops(shops);
//        }
//        return shops;
//    }

    public List<PickUpPoint> getAllPoints() {
        List<PickUpPoint> pickUpPoints = deliveryRedisRepository.getPickUpPoints();
        if (pickUpPoints != null) {
            return pickUpPoints;
        }
        pickUpPoints = pickUpPointRepository.findAll();
        if (!pickUpPoints.isEmpty()) {
            deliveryRedisRepository.putPickUpPoints(pickUpPoints);
        }
        return pickUpPoints;
    }

    public DeliveryPriceResponseDto getDeliveryPrice(DeliveryPriceRequestDto deliveryPriceRequestDto) {
        DeliveryPriceResponseDto deliveryPriceResponseDto = new DeliveryPriceResponseDto();
        for (DeliveryPriceConditions conditions : deliveryPrice.getPriceConfig().getPriceConfig().get(deliveryPriceRequestDto.getDeliveryType())) {
            if (conditions.getMinTotalPrice() <= deliveryPriceRequestDto.getTotalPrice()) {
                deliveryPriceResponseDto.setPrice((float) conditions.getPrice());
            }
        }
        return deliveryPriceResponseDto;
    }

    public PickUpPoint getPickUpPoint(Long id) {
        return pickUpPointRepository.findById(id).orElseThrow(() -> new ShopNotFoundException("There is no shop with id " + id));
    }
}
