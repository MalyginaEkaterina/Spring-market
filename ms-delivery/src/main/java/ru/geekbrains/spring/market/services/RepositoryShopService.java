package ru.geekbrains.spring.market.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.market.exceptions.ShopNotFoundException;
import ru.geekbrains.spring.market.model.Shop;
import ru.geekbrains.spring.market.repositories.ShopIdentityMap;
import ru.geekbrains.spring.market.repositories.ShopMapperRepo;

import java.util.List;

@Service
public class RepositoryShopService implements ShopService {
//    @Autowired
//    private ShopRepository shopRepository;

    @Autowired
    private ShopMapperRepo shopRepository;

    @Autowired
    private ShopIdentityMap shopIdentityMap;

    @Override
    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    @Override
    public Shop getShop(Long id) {
        Shop shop = shopIdentityMap.getShop(id);
        if (shop == null) {
            shop = shopRepository.findById(id).orElseThrow(() -> new ShopNotFoundException("There is no shop with id " + id));
            shopIdentityMap.addShop(shop);
        }
        return shop;
    }

    @Override
    public Shop addOrUpdateShop(Shop shop) {
        Shop resShop = shopRepository.save(shop);
        shopIdentityMap.addShop(shop);
        return resShop;
    }
}
