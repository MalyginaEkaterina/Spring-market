package ru.geekbrains.spring.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductReserveResponse {
    private Integer status;
    private List<ProductItemDto> maxQuantities;

    public ProductReserveResponse() {
        maxQuantities = new ArrayList<>();
    }

    public void addMaxQuantities(ProductItemDto productItemDto) {
        maxQuantities.add(productItemDto);
    }
}
