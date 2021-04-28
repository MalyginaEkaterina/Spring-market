package ru.geekbrains.spring.market.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.market.model.DeliveryType;
import ru.geekbrains.spring.market.model.PickUpPoint;
import ru.geekbrains.spring.market.model.Shop;

import java.util.List;

@Component
public class DeliveryRedisRepository {
    @Autowired
    private RedisTemplate<String, List<DeliveryType>> deliveryTypeRedisTemplate;

    @Autowired
    private RedisTemplate<String, List<Shop>> shopRedisTemplate;

    @Autowired
    RedisTemplate<String, List<PickUpPoint>> pickUpPointRedisTemplate;

    public void putDeliveryTypes(List<DeliveryType> deliveryTypes) {
        deliveryTypeRedisTemplate.opsForValue().set("deliveryTypes", deliveryTypes);
    }

    public List<DeliveryType> getDeliveryTypes() {
        return deliveryTypeRedisTemplate.opsForValue().get("deliveryTypes");
    }

    public void putShops(List<Shop> shops) {
        shopRedisTemplate.opsForValue().set("shops", shops);
    }

    public List<Shop> getShops() {
        return shopRedisTemplate.opsForValue().get("shops");
    }

    public void deleteShops() {
        shopRedisTemplate.opsForValue().getOperations().delete("shops");
    }

    public void putPickUpPoints(List<PickUpPoint> pickUpPoints) {
        pickUpPointRedisTemplate.opsForValue().set("pickUpPoints", pickUpPoints);
    }

    public List<PickUpPoint> getPickUpPoints() {
        return pickUpPointRedisTemplate.opsForValue().get("pickUpPoints");
    }

}
