package ru.geekbrains.spring.market.repositories;

import org.springframework.stereotype.Component;
import ru.geekbrains.spring.market.model.Shop;

import java.util.HashMap;
import java.util.Map;

@Component
public class ShopIdentityMap {
    private Map<Long, Shop> shopMap = new HashMap();

    public void addShop(Shop shop) {
        shopMap.put(shop.getId(), shop);
    }

    public Shop getShop(Long id) {
        return shopMap.get(id);
    }

    public void printMap() {
        System.out.println(shopMap);
    }
}
