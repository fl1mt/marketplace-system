package com.marketplace.order;
import com.marketplace.events.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

    public interface OrdersRepository extends JpaRepository<Order, UUID> {
        List<Order> findAllByUserId(UUID userId);

        List<Order> findByUserIdAndOrderStatusNotIn(UUID userId, List<OrderStatus> statuses);

        Optional<Order> findByIdAndUserId(UUID id, UUID userId);
    }
