package ru.geekbrains.spring.market.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class FullBasketDto {
    private Long id;
    private ProductBasketDto product;
    private Integer quantity;
}
