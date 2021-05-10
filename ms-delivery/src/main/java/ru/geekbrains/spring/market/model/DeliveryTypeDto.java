package ru.geekbrains.spring.market.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryTypeDto {
    @JsonUnwrapped private DeliveryType deliveryType;
}
