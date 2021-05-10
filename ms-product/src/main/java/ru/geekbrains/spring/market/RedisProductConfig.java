package ru.geekbrains.spring.market;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import ru.geekbrains.spring.market.configurations.RedisConfig;
import ru.geekbrains.spring.market.model.FullProductDto;
import ru.geekbrains.spring.market.model.PageProductDto;

@Configuration
@Import(RedisConfig.class)
public class RedisProductConfig {

    private final RedisConfig redisConfig;

    public RedisProductConfig(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
    }

    @Bean
    public RedisTemplate<String, FullProductDto> productRedisTemplate() {
        RedisTemplate<String, FullProductDto> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConfig.jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(FullProductDto.class));
        return template;
    }

    @Bean
    public RedisTemplate<String, PageProductDto> productsPageRedisTemplate() {
        RedisTemplate<String, PageProductDto> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConfig.jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(PageProductDto.class));
        return template;
    }
}
