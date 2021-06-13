package ru.geekbrains.spring.market.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.market.exceptions.ShopNotFoundException;
import ru.geekbrains.spring.market.model.Shop;
import ru.geekbrains.spring.market.repositories.ShopRepository;

import java.util.List;

@Service
public class RepositoryShopService implements ShopService {
    @Autowired
    private ShopRepository shopRepository;

    @Override
    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    @Override
    public Shop getShop(Long id) {
        return shopRepository.findById(id).orElseThrow(() -> new ShopNotFoundException("There is no shop with id " + id));
    }

    @Override
    public Shop addOrUpdateShop(Shop shop) {
        return shopRepository.save(shop);
    }
}
