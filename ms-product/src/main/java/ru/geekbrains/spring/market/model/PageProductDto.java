package ru.geekbrains.spring.market.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageProductDto implements Serializable {
    private Integer countOfPages;
    private Integer page;
    private List<ProductDto> products;
}
