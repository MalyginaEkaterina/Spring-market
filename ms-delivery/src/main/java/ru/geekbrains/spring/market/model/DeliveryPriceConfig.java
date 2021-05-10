package ru.geekbrains.spring.market.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DeliveryPriceConfig {
    private Map<String, List<DeliveryPriceConditions>> priceConfig;
}
