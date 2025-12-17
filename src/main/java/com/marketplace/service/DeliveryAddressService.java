package com.marketplace.service;

import com.marketplace.dto.DeliveryAddressRequestDTO;
import com.marketplace.dto.DeliveryAddressResponseDTO;
import com.marketplace.entity.DeliveryAddress;
import com.marketplace.entity.User;
import com.marketplace.exceptions.DuplicateException;
import com.marketplace.mapper.DeliveryAddressMapper;
import com.marketplace.repository.DeliveryAddressesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeliveryAddressService {
    private final DeliveryAddressMapper deliveryAddressMapper;
    private final DataAuthService dataAuthService;
    private final DeliveryAddressesRepository deliveryAddressesRepository;

    public DeliveryAddressService(DeliveryAddressMapper deliveryAddressMapper,
                                  DataAuthService dataAuthService, DeliveryAddressesRepository deliveryAddressesRepository) {
        this.deliveryAddressMapper = deliveryAddressMapper;
        this.dataAuthService = dataAuthService;
        this.deliveryAddressesRepository = deliveryAddressesRepository;
    }

    public DeliveryAddressResponseDTO addUserDeliveryAddress(UUID userId, DeliveryAddressRequestDTO deliveryAddressRequestDTO){
        User user = dataAuthService.checkUsersId(userId);

        String city = deliveryAddressRequestDTO.getCity().toLowerCase().trim();
        String street = deliveryAddressRequestDTO.getStreet().toLowerCase().trim();
        String house = deliveryAddressRequestDTO.getHouse().toLowerCase().trim();
        String apartment = deliveryAddressRequestDTO.getApartment().toLowerCase().trim();
        String index = deliveryAddressRequestDTO.getIndex().trim();

        Optional<DeliveryAddress> existingAddress = deliveryAddressesRepository
                .findByUserIdAndCityIgnoreCaseAndStreetIgnoreCaseAndHouseIgnoreCaseAndApartmentIgnoreCaseAndIndex(
                userId,
                city,
                street,
                house,
                apartment,
                index
        );

        if (existingAddress.isPresent()) {
            throw new DuplicateException("That's address has exists!");
        }

        DeliveryAddress newDeliveryAddress = deliveryAddressMapper.toEntity(deliveryAddressRequestDTO);
        newDeliveryAddress.setUser(user);

        deliveryAddressesRepository.save(newDeliveryAddress);
        return deliveryAddressMapper.toDeliveryAddressDTO(newDeliveryAddress);
    }

    public List<DeliveryAddressResponseDTO> getUsersDeliveryAddresses(UUID userId){
        User user = dataAuthService.checkUsersId(userId);
        List<DeliveryAddress> deliveryAddresses = deliveryAddressesRepository.findAllByUserId(user.getId());
        return deliveryAddressMapper.toDeliveryAddressesDto(deliveryAddresses);

    }
}
