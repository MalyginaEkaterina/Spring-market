package ru.geekbrains.spring.market;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.geekbrains.spring.market.model.PickUpPoint;
import ru.geekbrains.spring.market.model.Shop;
import ru.geekbrains.spring.market.repositories.PickUpPointRepository;
import ru.geekbrains.spring.market.repositories.ShopRepository;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class RepositoriesTest {
    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private PickUpPointRepository pickUpPointRepository;

    @Test
    public void initDbShopTest() {
        List<Shop> shops = shopRepository.findAll();
        Assertions.assertEquals(3, shops.size());
    }

    @Test
    public void initDbPickUpPointTest() {
        List<PickUpPoint> points = pickUpPointRepository.findAll();
        Assertions.assertEquals(2, points.size());
    }
}
