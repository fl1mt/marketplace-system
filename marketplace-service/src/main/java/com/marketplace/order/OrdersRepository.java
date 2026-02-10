package com.marketplace.order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

    public interface OrdersRepository extends JpaRepository<Order, UUID> {
        List<Order> findAllByUserId(UUID userId);

        List<Order> findByUserIdAndOrderStatusNotIn(UUID userId, List<OrderStatus> statuses);
    }
