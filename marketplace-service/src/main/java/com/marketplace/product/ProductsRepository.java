package com.marketplace.product;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.Optional;
import java.util.UUID;

public interface ProductsRepository extends JpaRepository<Product, UUID> {
    @Modifying
    @Query("update Product p set p.inventoryStock = p.inventoryStock - :quantity where p.id = :productId and p.inventoryStock >= :quantity")
    int reserveStock(@Param("productId") UUID productId, @Param("quantity") int quantity);

    @Modifying
    @Query("update Product p set p.inventoryStock = p.inventoryStock + :quantity where p.id = :productId")
    int returnStock(@Param("productId") UUID productId, @Param("quantity") int quantity);

    @Modifying
    @Query("update Product p set p.inventoryStock = p.inventoryStock + :amount where p.id = :productId and p.inventoryStock + :amount >= 0")
    int adjustStock(@Param("productId") UUID productId, @Param("amount") int amount);
}
