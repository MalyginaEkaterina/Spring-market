package ru.geekbrains.spring.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "products_items")
@Data
@NoArgsConstructor
public class ProductItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "id_product")
    private Integer productId;

    @ManyToOne
    @JoinColumn(name = "id_storage")
    private Storage storage;

    @Column
    private Integer quantity;

    @Column
    private String place;
}
