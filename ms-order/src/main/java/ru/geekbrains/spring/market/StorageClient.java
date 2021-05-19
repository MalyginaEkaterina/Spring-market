package ru.geekbrains.spring.market;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.geekbrains.spring.market.model.ProductItemDto;
import ru.geekbrains.spring.market.model.ProductReserveResponse;

import java.util.List;

@FeignClient("ms-storage")
public interface StorageClient {
    @PostMapping("/storage/reserve")
    ProductReserveResponse reserveProducts(@RequestBody List<ProductItemDto> listProductItemDto);
}
