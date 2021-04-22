package ru.geekbrains.spring.market.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.market.model.Basket;
import ru.geekbrains.spring.market.model.BasketDto;
import ru.geekbrains.spring.market.repositories.BasketProductRepository;

import java.util.UUID;

@Service
public class BasketProductService {
    @Autowired
    private BasketProductRepository basketProductRepository;

    public void add(Basket basketProduct){
        basketProductRepository.save(basketProduct);
    }

    @Transactional
    public int updateCountByIdAndGuid(UUID guid, BasketDto basketProduct) {
        return 0;
//        return basketProductRepository.updateCountByIdAndGuid(guid, basketProduct.getId(), basketProduct.getProductCount());
    }

    @Transactional
    public int deleteByIdAndGuid(UUID guid, Long id) {
        return basketProductRepository.deleteByIdAndSessionId(guid, id);
    }
}
