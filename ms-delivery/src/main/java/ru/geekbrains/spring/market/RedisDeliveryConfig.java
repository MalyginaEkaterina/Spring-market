package ru.geekbrains.spring.market;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import ru.geekbrains.spring.market.configurations.RedisConfig;
import ru.geekbrains.spring.market.model.PickUpPoint;
import ru.geekbrains.spring.market.model.Shop;

import java.util.List;


@Configuration
@Import(RedisConfig.class)
public class RedisDeliveryConfig {

    private final RedisConfig redisConfig;

    public RedisDeliveryConfig(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
    }

    @Bean
    public RedisTemplate<String, List<Shop>> shopRedisTemplate() {
        RedisTemplate<String, List<Shop>> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConfig.jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    @Bean
    public RedisTemplate<String, List<PickUpPoint>> pickUpPointRedisTemplate() {
        RedisTemplate<String, List<PickUpPoint>> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConfig.jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}
