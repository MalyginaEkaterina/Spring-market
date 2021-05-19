package ru.geekbrains.spring.market.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.spring.market.model.ProductItemDto;
import ru.geekbrains.spring.market.model.ProductReserveResponse;
import ru.geekbrains.spring.market.services.StorageService;

import java.util.List;

@RestController
@RequestMapping
public class StorageController {

    @Autowired
    private StorageService storageService;

    @PostMapping("/reserve")
    public ProductReserveResponse reserveProducts(@RequestBody List<ProductItemDto> listProductItemDto) {
        return storageService.reserveProducts(listProductItemDto);
    }
}
