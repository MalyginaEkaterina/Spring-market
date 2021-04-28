package ru.geekbrains.spring.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products_comments")
@Data
@NoArgsConstructor
public class ProductComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product")
    private Product product;
    @Column(name = "id_user")
    private Integer userId;
    @Column
    private Integer rating;
    @Column
    private String comment;
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    public ProductComment(AddProductCommentDto commentDto) {
        this.userId = commentDto.getUserId();
        this.rating = commentDto.getRating();
        this.comment = commentDto.getComment();
    }
}
