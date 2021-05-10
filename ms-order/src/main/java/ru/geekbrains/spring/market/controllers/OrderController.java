package ru.geekbrains.spring.market.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.market.Const;
import ru.geekbrains.spring.market.configurations.jwt.JwtProvider;
import ru.geekbrains.spring.market.model.OrderRequestDto;
import ru.geekbrains.spring.market.model.OrderResponseDto;
import ru.geekbrains.spring.market.model.PromoCodeDto;
import ru.geekbrains.spring.market.model.PromoDto;
import ru.geekbrains.spring.market.services.OrderService;

@RestController
@RequestMapping
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/promo")
    public PromoDto applyPromo(@RequestBody PromoCodeDto promoCodeDto) {
        return orderService.applyPromo(promoCodeDto);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/promo/generate")
    public PromoCodeDto generatePromo(@RequestBody PromoDto promoDto) {
        return orderService.generate(promoDto);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public OrderResponseDto createOrder(@RequestHeader(Const.AUTHORIZATION) String token, @RequestBody OrderRequestDto orderRequestDto) {
        // TODO: 03.05.2021 запрос в storage
        return orderService.createOrder(jwtProvider.getUserIdFromToken(token.substring(7)), orderRequestDto);
    }
}
