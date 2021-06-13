package ru.geekbrains.spring.market.services;

import ru.geekbrains.spring.market.model.Shop;

import java.util.List;

public interface ShopService {
    List<Shop> getAllShops();

    Shop getShop(Long id);

    Shop addOrUpdateShop(Shop shop);
}
