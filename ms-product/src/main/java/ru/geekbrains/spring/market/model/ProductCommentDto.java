package ru.geekbrains.spring.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ProductCommentDto {
    private Long id;
    private Integer userId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;

    public ProductCommentDto(ProductComment productComment) {
        this.id = productComment.getId();
        this.userId = productComment.getUserId();
        this.rating = productComment.getRating();
        this.comment = productComment.getComment();
        this.createdAt = productComment.getCreatedAt();
    }
}
