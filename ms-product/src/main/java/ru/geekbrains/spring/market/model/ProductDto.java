package ru.geekbrains.spring.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ProductDto implements Serializable {
    private Integer id;
    private String title;
    private Double price;
    private List<CategoryDto> categories;

    public ProductDto(Product p) {
        this.id = p.getId();
        this.title = p.getTitle();
        this.price = p.getPrice();
        this.categories = p.getCategories() == null ? new ArrayList<>() : p.getCategories().stream().map(CategoryDto::new).collect(Collectors.toList());
    }
}
