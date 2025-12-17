package com.marketplace.repository;

import com.marketplace.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findAllByProductId(UUID productId);
    boolean existsByUserIdAndProductId(UUID userId, UUID productId);
}
