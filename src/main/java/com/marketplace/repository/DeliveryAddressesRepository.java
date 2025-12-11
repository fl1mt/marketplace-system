package com.marketplace.repository;

import com.marketplace.entity.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryAddressesRepository extends JpaRepository<DeliveryAddress, UUID> {
    List<DeliveryAddress> findAllByUserId(UUID uuid);
    Optional<DeliveryAddress> findByUserIdAndCityIgnoreCaseAndStreetIgnoreCaseAndHouseIgnoreCaseAndApartmentIgnoreCaseAndIndex(
            UUID userId,
            String city,
            String street,
            String house,
            String apartment,
            String index
    );
    Optional<DeliveryAddress> findByUserId(UUID uuid);
    Optional<DeliveryAddress> findByIdAndUserId(UUID id, UUID userId);
}
