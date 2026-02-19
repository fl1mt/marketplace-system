package com.marketplace.cart;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final StringRedisTemplate redisTemplate;

    public CartService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addToCart(UUID userId, UUID productId, int quantity) {
        String key = buildKey(userId);

        redisTemplate.opsForHash()
                .increment(key, productId.toString(), quantity);

        redisTemplate.expire(key, Duration.ofHours(24));
    }

    public Map<UUID, Integer> getCart(UUID userId) {
        String key = buildKey(userId);

        Map<Object, Object> raw = redisTemplate
                .opsForHash()
                .entries(key);

        return raw.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> UUID.fromString((String) e.getKey()),
                        e -> Integer.parseInt((String) e.getValue())
                ));
    }

    public void removeItem(UUID userId, UUID productId) {
        redisTemplate.opsForHash()
                .delete(buildKey(userId), productId.toString());
    }

    public void clearCart(UUID userId) {
        redisTemplate.delete(buildKey(userId));
    }

    private String buildKey(UUID userId) {
        return "cart:" + userId;
    }
}