package com.marketplace.service;
import com.marketplace.entity.DeliveryAddress;
import com.marketplace.entity.User;
import com.marketplace.repository.DeliveryAddressesRepository;
import com.marketplace.repository.UsersRepository;
import org.springframework.data.elasticsearch.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DataAuthService {
    private final UsersRepository usersRepository;
    private final DeliveryAddressesRepository deliveryAddressesRepository;
    public DataAuthService(UsersRepository usersRepository, DeliveryAddressesRepository deliveryAddressesRepository) {
        this.usersRepository = usersRepository;
        this.deliveryAddressesRepository = deliveryAddressesRepository;
    }
    public User checkUsersId(UUID userId){
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found or access denied"));
        return user;
    }
    public DeliveryAddress checkUsersDeliveryAddress(UUID deliveryAddressId, UUID userId){
        DeliveryAddress deliveryAddress = deliveryAddressesRepository.findByIdAndUserId(deliveryAddressId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found or access denied"));
        return deliveryAddress;
    }
}
