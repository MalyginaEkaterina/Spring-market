package ru.geekbrains.spring.market;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.geekbrains.spring.market.model.DeliveryInfoRequestDto;
import ru.geekbrains.spring.market.model.DeliveryInfoResponseDto;

@FeignClient("ms-delivery")
public interface DeliveryClient {
    @PostMapping("/delivery/delivery_details")
    DeliveryInfoResponseDto getDeliveryDetails(@RequestBody DeliveryInfoRequestDto deliveryInfoRequestDto);
}
