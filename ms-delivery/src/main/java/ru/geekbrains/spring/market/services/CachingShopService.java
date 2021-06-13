package ru.geekbrains.spring.market.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.market.model.Shop;
import ru.geekbrains.spring.market.repositories.DeliveryRedisRepository;

import java.util.List;

@Service
public class CachingShopService implements ShopService {
    private ShopService inner;

    private DeliveryRedisRepository deliveryRedisRepository;

    @Autowired
    public CachingShopService(
            RepositoryShopService inner,
            DeliveryRedisRepository deliveryRedisRepository
    ) {
        this.inner = inner;
        this.deliveryRedisRepository = deliveryRedisRepository;
    }

    @Override
    public List<Shop> getAllShops() {
        List<Shop> shops = deliveryRedisRepository.getShops();
        if (shops != null) {
            return shops;
        }
        shops = inner.getAllShops();
        deliveryRedisRepository.putShops(shops);
        return shops;
    }

    @Override
    public Shop getShop(Long id) {
        return inner.getShop(id);
    }

    public Shop addOrUpdateShop(Shop shop) {
        deliveryRedisRepository.deleteShops();
        return inner.addOrUpdateShop(shop);
    }
}
