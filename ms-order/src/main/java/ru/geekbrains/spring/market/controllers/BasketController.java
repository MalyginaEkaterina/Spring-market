package ru.geekbrains.spring.market.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.market.Const;
import ru.geekbrains.spring.market.configurations.jwt.JwtProvider;
import ru.geekbrains.spring.market.exceptions.ProductNotFoundException;
import ru.geekbrains.spring.market.model.BasketDto;
import ru.geekbrains.spring.market.model.Session;
import ru.geekbrains.spring.market.services.BasketService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/basket")
public class BasketController {
    @Autowired
    private BasketService basketService;

    @Autowired
    private JwtProvider jwtProvider;

    @GetMapping
    public List<BasketDto> getBasket(@CookieValue(value = "session_guid", required = false) UUID guid,
                                     @RequestHeader(value = Const.AUTHORIZATION, required = false) String token) {
        if (token != null && jwtProvider.validateToken(token.substring(7))) {
            return basketService.getBasketByUserId(jwtProvider.getUserIdFromToken(token.substring(7)));
        } else if (guid != null) {
            return basketService.getBasket(guid);
        } else {
            throw new ProductNotFoundException("The cart is empty");
        }
    }

    @PostMapping("/add_product")
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@CookieValue(value = "session_guid", required = false) UUID guid,
                              @RequestHeader(value = Const.AUTHORIZATION, required = false) String token,
                              @RequestBody BasketDto basketProduct,
                              HttpServletResponse response) {
        basketProduct.setId(null);
        if (token != null && jwtProvider.validateToken(token.substring(7))) {
            basketService.addByUserId(jwtProvider.getUserIdFromToken(token.substring(7)), basketProduct);
        } else if (guid != null) {
            basketService.add(guid, basketProduct);
        } else {
            Session session = basketService.getSession();
            basketService.add(session.getGuid(), basketProduct);
            Cookie cookie = new Cookie("session_guid", session.getGuid().toString());
            cookie.setMaxAge(60*60*24);
            response.addCookie(cookie);
        }
    }

    @PutMapping("/update")
    public void update(@CookieValue(value = "session_guid", required = false) UUID guid,
                       @RequestHeader(value = Const.AUTHORIZATION, required = false) String token,
                       @RequestBody BasketDto basketProduct) {
        if (token != null && jwtProvider.validateToken(token.substring(7))) {
            basketService.updateByUserId(jwtProvider.getUserIdFromToken(token.substring(7)), basketProduct);
        } else if (guid != null) {
            basketService.update(guid, basketProduct);
        } else {
            throw new ProductNotFoundException("The cart is empty");
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@CookieValue(value = "session_guid", required = false) UUID guid,
                       @RequestHeader(value = Const.AUTHORIZATION, required = false) String token,
                       @PathVariable Long id) {
        if (token != null && jwtProvider.validateToken(token.substring(7))) {
            basketService.deleteByUserId(jwtProvider.getUserIdFromToken(token.substring(7)), id);
        } else if (guid != null) {
            basketService.delete(guid, id);
        } else {
            throw new ProductNotFoundException("The cart is empty");
        }
    }

}
