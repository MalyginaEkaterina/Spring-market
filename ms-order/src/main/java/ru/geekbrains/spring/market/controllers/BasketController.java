package ru.geekbrains.spring.market.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.market.model.BasketDto;
import ru.geekbrains.spring.market.model.Session;
import ru.geekbrains.spring.market.services.BasketService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions")
public class BasketController {
    @Autowired
    private BasketService sessionService;

    @GetMapping
    public Session getSession() {
        return sessionService.getSession();
    }

    @GetMapping("/basket")
    public List<BasketDto> getBasket(@CookieValue("session_guid") UUID guid) {
        return sessionService.getBasket(guid);
    }

    @PostMapping("/basket")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@CookieValue("session_guid") UUID guid, @RequestBody BasketDto basketProduct) {
        basketProduct.setId(null);
        sessionService.add(guid, basketProduct);
    }

    @PutMapping("/basket")
    public void update(@CookieValue("session_guid") UUID guid, @RequestBody BasketDto basketProduct) {
        sessionService.update(guid, basketProduct);
    }

    @DeleteMapping("/basket/{id}")
    public void delete(@CookieValue("session_guid") UUID guid, @PathVariable Long id) {
        sessionService.delete(guid, id);
    }

}
