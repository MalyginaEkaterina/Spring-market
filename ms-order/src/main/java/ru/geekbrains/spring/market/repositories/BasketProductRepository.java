package ru.geekbrains.spring.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.geekbrains.spring.market.model.Basket;

import java.util.List;
import java.util.UUID;

@Repository
public interface BasketProductRepository extends JpaRepository<Basket, Long> {
    @Modifying
    @Query(value = "delete from basket b where b.id = :id and b.session_guid = :guid", nativeQuery = true)
    int deleteByIdAndSessionId(@Param("guid") UUID guid, @Param("id") Long id);

    @Modifying
    @Query(value = "update basket b set b.quantity = :count where b.id = :id and b.session_guid = :guid", nativeQuery = true)
    int updateCountByIdAndGuid(@Param("guid") UUID guid, @Param("id") Long id, @Param("count") Integer count);

    List<Basket> findByUserID(Integer userID);

    @Modifying
    @Query(value = "update basket b set b.quantity = :count where b.id = :id and b.id_user = :userId", nativeQuery = true)
    int updateCountByIdAndUserId(@Param("userId") Integer userId, @Param("id") Long id, @Param("count") Integer count);

    @Modifying
    @Query(value = "delete from basket b where b.id = :id and b.id_user = :userId", nativeQuery = true)
    int deleteByIdAndUserId(@Param("userId") Integer userId, @Param("id") Long id);

    @Modifying
    @Query(value = "update basket b set b.id_user = :userId where b.session_guid = :guid", nativeQuery = true)
    int setUserIdOnSession(@Param("guid") UUID guid, @Param("userId") Integer userId);

    @Modifying
    @Query(value = "delete from basket b where b.id in :listId", nativeQuery = true)
    int deleteByIds(@Param("listId") List<Long> ids);
}
