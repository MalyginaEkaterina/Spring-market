package ru.geekbrains.spring.market.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.market.model.DeliveryType;
import ru.geekbrains.spring.market.model.PickUpPoint;
import ru.geekbrains.spring.market.model.Shop;
import ru.geekbrains.spring.market.repositories.DeliveryRedisRepository;
import ru.geekbrains.spring.market.repositories.PickUpPointRepository;
import ru.geekbrains.spring.market.repositories.ShopRepository;

import java.util.List;

@Service
public class DeliveryService {
    @Autowired
    private PickUpPointRepository pickUpPointRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private DeliveryRedisRepository deliveryRedisRepository;

    public List<DeliveryType> getAllTypes() {
        List<DeliveryType> deliveryTypes = deliveryRedisRepository.getDeliveryTypes();
        if (deliveryTypes != null) {
            return deliveryTypes;
        }
        deliveryTypes = deliveryTypeRepository.findAll();
        if (!deliveryTypes.isEmpty()) {
            deliveryRedisRepository.putDeliveryTypes(deliveryTypes);
        }
        return deliveryTypes;
    }

    public List<Shop> getAllShops() {
        List<Shop> shops = deliveryRedisRepository.getShops();
        if (shops != null) {
            return shops;
        }
        shops = shopRepository.findAll();
        if (!shops.isEmpty()) {
            deliveryRedisRepository.putShops(shops);
        }
        return shops;
    }

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

    public Shop addOrUpdateShop(Shop shopDto) {
        Shop shop = shopRepository.save(shopDto);
        deliveryRedisRepository.deleteShops();
        return shop;
    }
}
