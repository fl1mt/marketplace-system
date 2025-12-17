package com.marketplace.mapper;

import com.marketplace.dto.OrderRequestDTO;
import com.marketplace.dto.OrderResponseDTO;
import com.marketplace.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, DeliveryAddressMapper.class})
public interface OrderMapper {
    Order toEntity(OrderRequestDTO requestDTO);
    @Mapping(target = "userPublicDTO", source = "user")
    @Mapping(target = "deliveryAddressResponseDTO", source = "address")
    OrderResponseDTO toResponseDTO(Order order);
}
