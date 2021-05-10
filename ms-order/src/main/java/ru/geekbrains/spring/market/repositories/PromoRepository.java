package ru.geekbrains.spring.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.geekbrains.spring.market.model.Promo;

public interface PromoRepository extends JpaRepository<Promo, Long> {
    @Query(value = "select * from promo p where p.promo_code = :promoCode and p.is_applied = 0 and p.exp_date > now()", nativeQuery = true)
    Promo getPromo(@Param("promoCode") String promoCode);
}
