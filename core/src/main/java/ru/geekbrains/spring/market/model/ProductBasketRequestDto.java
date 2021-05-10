package ru.geekbrains.spring.market.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductBasketRequestDto {
    private List<Integer> listProductId;
}
