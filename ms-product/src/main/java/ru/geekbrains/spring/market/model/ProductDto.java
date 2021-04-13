package ru.geekbrains.spring.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ProductDto {
    private Integer id;
    private String title;
    private Double cost;
    private List<CategoryDto> categories;

    public ProductDto(Product p) {
        this.id = p.getId();
        this.title = p.getTitle();
        this.cost = p.getCost();
        this.categories = p.getCategories() == null ? null : p.getCategories().stream().map(CategoryDto::new).collect(Collectors.toList());
    }
}
