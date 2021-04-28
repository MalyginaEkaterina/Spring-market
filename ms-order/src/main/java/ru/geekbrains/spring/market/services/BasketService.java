package ru.geekbrains.spring.market.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.market.exceptions.ProductNotFoundException;
import ru.geekbrains.spring.market.exceptions.SessionExpiredException;
import ru.geekbrains.spring.market.model.Basket;
import ru.geekbrains.spring.market.model.BasketDto;
import ru.geekbrains.spring.market.model.Session;
import ru.geekbrains.spring.market.repositories.BasketProductRepository;
import ru.geekbrains.spring.market.repositories.SessionRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BasketService {
    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private BasketProductRepository basketProductRepository;

    public Session getSession() {
        return sessionRepository.save(new Session());
    }

    public List<BasketDto> getBasket(UUID guid) {
        try {
            return sessionRepository.findById(guid).get().getBasketProducts().stream()
                    .map(BasketDto::new)
                    .collect(Collectors.toList());
        } catch (NoSuchElementException e) {
            throw new SessionExpiredException("Session was expired");
        }
    }

    public List<BasketDto> getBasketByUserId(Integer userId) {
        return basketProductRepository.findByUserID(userId).stream().map(BasketDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void add(UUID guid, BasketDto basketProductDto) {
        Session s = sessionRepository.findById(guid).orElseThrow(() -> new SessionExpiredException("Session was expired"));
        Basket b = new Basket(basketProductDto);
        b.setSession(s);
        basketProductRepository.save(b);
        sessionRepository.updateSessionUpdatedAt(guid);
    }

    public void addByUserId(Integer userId, BasketDto basketProductDto) {
        Basket basket = new Basket(basketProductDto);
        basket.setSession(null);
        basket.setUserID(userId);
        basketProductRepository.save(basket);
    }

    @Transactional
    public void delete(UUID guid, Long id) {
        if (basketProductRepository.deleteByIdAndSessionId(guid, id) > 0) {
            sessionRepository.updateSessionUpdatedAt(guid);
        } else {
            throw new ProductNotFoundException("There is no product with id " + id + " in the basket");
        }
    }

    public void deleteByUserId(Integer userId, Long id) {
        if (!(basketProductRepository.deleteByIdAndUserId(userId, id) > 0)) {
            throw new ProductNotFoundException("There is no product with id " + id + " in the basket");
        }
    }

    @Transactional
    public void update(UUID guid, BasketDto basketProduct) {
        if (basketProductRepository.updateCountByIdAndGuid(guid, basketProduct.getId(), basketProduct.getQuantity()) > 0) {
            sessionRepository.updateSessionUpdatedAt(guid);
        } else {
            throw new ProductNotFoundException("There is no product with id " + basketProduct.getId() + " in the basket");
        }
    }

    public void updateByUserId(Integer userId, BasketDto basketProduct) {
        if (!(basketProductRepository.updateCountByIdAndUserId(userId, basketProduct.getId(), basketProduct.getQuantity()) > 0)) {
            throw new ProductNotFoundException("There is no product with id " + basketProduct.getId() + " in the basket");
        }
    }
}
