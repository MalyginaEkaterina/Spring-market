package ru.geekbrains.spring.market.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductItemDto {
    private Integer productId;
    private Integer quantity;
}
