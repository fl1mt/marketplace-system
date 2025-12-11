package com.marketplace.mapper;

import com.marketplace.dto.DeliveryAddressRequestDTO;
import com.marketplace.dto.DeliveryAddressResponseDTO;
import com.marketplace.entity.DeliveryAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface DeliveryAddressMapper {

    DeliveryAddress toEntity(DeliveryAddressRequestDTO requestDTO);

    @Mapping(target = "userResponseDTO", source = "user")
    DeliveryAddressResponseDTO toDeliveryAddressDTO(DeliveryAddress deliveryAddress);

    List<DeliveryAddressResponseDTO> toDeliveryAddressesDto(List<DeliveryAddress> deliveryAddresses);

}
