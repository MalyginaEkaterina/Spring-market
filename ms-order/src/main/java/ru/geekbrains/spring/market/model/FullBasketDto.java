package ru.geekbrains.spring.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FullBasketDto {
    private Long id;
    private ProductBasketDto product;
    private Integer quantity;
}
