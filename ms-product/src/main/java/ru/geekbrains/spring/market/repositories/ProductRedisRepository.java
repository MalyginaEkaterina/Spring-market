package ru.geekbrains.spring.market.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.market.model.PageProductDto;
import ru.geekbrains.spring.market.model.ProductDto;

import java.util.Optional;

@Component
public class ProductRedisRepository {
    @Autowired
    private RedisTemplate<String, ProductDto> productRedisTemplate;
    @Autowired
    private RedisTemplate<String, PageProductDto> productsPageRedisTemplate;

    public Optional<ProductDto> getProduct(Integer id) {
        return Optional.ofNullable(
                productRedisTemplate.opsForValue().get("product:" + id)
        );
    }

    public void putProduct(ProductDto product) {
        productRedisTemplate.opsForValue().set("product:" + product.getId(), product);
    }

    public void deleteProduct(Integer id) {
        productRedisTemplate.opsForValue().getOperations().delete("product:" + id);
    }

    public void putPage(int page, PageProductDto products) {
        productsPageRedisTemplate.opsForValue().set("page:" + page, products);
    }

    public PageProductDto getPage(int page) {
        return productsPageRedisTemplate.opsForValue().get("page:" + page);
    }

    public void deletePages() {
        productsPageRedisTemplate.delete(productsPageRedisTemplate.keys("page:*"));
    }
}
