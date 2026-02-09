package com.marketplace.logisticservice.delivery;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {
    List<DeliveryResponse> toResponseDtoList(List<Delivery> deliveries);
}
