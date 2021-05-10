package ru.geekbrains.spring.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ProductBasketDto implements Serializable {
    private Integer id;
    private String title;
    private Double price;
}
