package ru.geekbrains.spring.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class FullProductDto{
    private Integer id;
    private String title;
    private Double price;
    private String description;
    private String pictureUrl;
    private List<CategoryDto> categories;
    private List<ProductCommentDto> comments;

    public FullProductDto(Product p) {
        this.id = p.getId();
        this.title = p.getTitle();
        this.price = p.getPrice();
        this.description = p.getDescription();
        this.pictureUrl = p.getPictureUrl();
        this.categories = p.getCategories() == null ? new ArrayList<>() : p.getCategories().stream().map(CategoryDto::new)
                .collect(Collectors.toList());
        this.comments = p.getComments() == null ? new ArrayList<>() : p.getComments().stream().map(ProductCommentDto::new)
                .collect(Collectors.toList());
    }
}
