package ru.geekbrains.spring.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.geekbrains.spring.market.model.ProductItem;

import java.util.List;

public interface StorageRepository extends JpaRepository<ProductItem, Long> {
    @Query(value = "select * from products_items i where i.id_product in :listId", nativeQuery = true)
    List<ProductItem> getByProductIds(@Param("listId") List<Integer> ids);
}

