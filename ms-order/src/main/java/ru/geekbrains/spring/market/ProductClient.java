package ru.geekbrains.spring.market;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.geekbrains.spring.market.model.ProductBasketDto;
import ru.geekbrains.spring.market.model.ProductBasketRequestDto;

import java.util.List;

@FeignClient("ms-product")
public interface ProductClient {
    @PostMapping("/products/by_ids")
    List<ProductBasketDto> getProductsByIds(@RequestBody ProductBasketRequestDto productIds);
}
