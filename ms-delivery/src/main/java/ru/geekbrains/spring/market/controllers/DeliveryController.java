package ru.geekbrains.spring.market.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.market.model.DeliveryType;
import ru.geekbrains.spring.market.model.PickUpPoint;
import ru.geekbrains.spring.market.model.Shop;
import ru.geekbrains.spring.market.services.DeliveryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping("/types")
    public List<DeliveryType> getAllTypes() {
        return deliveryService.getAllTypes();
    }

    @GetMapping("/shops")
    public List<Shop> getAllShops() {
        return deliveryService.getAllShops();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add_shop")
    @ResponseStatus(HttpStatus.CREATED)
    public Shop addShop(@RequestBody Shop shop) {
        shop.setId(null);
        return deliveryService.addOrUpdateShop(shop);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update_shop")
    public Shop updateShop(@RequestBody Shop shop) {
        return deliveryService.addOrUpdateShop(shop);
    }

    @GetMapping("/pick_up_points")
    public List<PickUpPoint> getAllPoints() {
        return deliveryService.getAllPoints();
    }
}
