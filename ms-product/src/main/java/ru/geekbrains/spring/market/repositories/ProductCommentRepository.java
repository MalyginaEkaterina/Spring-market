package ru.geekbrains.spring.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.spring.market.model.ProductComment;

@Repository
public interface ProductCommentRepository extends JpaRepository<ProductComment, Long> {
}