package ru.geekbrains.spring.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "basket")
@Data
@NoArgsConstructor
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name = "session_guid")
    private Session session;

    @Column(name = "id_user")
    private Integer userID;

    @Column(name = "id_product")
    private Integer productId;

    @Column(name = "quantity")
    private Integer quantity;

    public Basket(BasketDto dto) {
        this.id = dto.getId();
        this.productId = dto.getProductId();
        this.quantity = dto.getQuantity();
    }
}
