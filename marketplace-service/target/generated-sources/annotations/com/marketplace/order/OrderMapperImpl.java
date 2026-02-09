package com.marketplace.order;

import com.marketplace.delivery.DeliveryAddressMapper;
import com.marketplace.user.UserMapper;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-10T04:14:48+0500",
    comments = "version: 1.6.2, compiler: javac, environment: Java 24.0.2 (Amazon.com Inc.)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DeliveryAddressMapper deliveryAddressMapper;

    @Override
    public Order toEntity(OrderRequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        Order order = new Order();

        return order;
    }

    @Override
    public OrderResponseDTO toResponseDTO(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();

        orderResponseDTO.setUserPublicDTO( userMapper.toPublicDTO( order.getUser() ) );
        orderResponseDTO.setDeliveryAddressResponseDTO( deliveryAddressMapper.toDeliveryAddressDTO( order.getAddress() ) );
        orderResponseDTO.setShippingCost( order.getShippingCost() );
        orderResponseDTO.setDeliveryDate( order.getDeliveryDate() );
        orderResponseDTO.setSubtotal( order.getSubtotal() );
        orderResponseDTO.setFinalTotal( order.getFinalTotal() );
        orderResponseDTO.setCreatedAt( order.getCreatedAt() );
        orderResponseDTO.setOrderStatus( order.getOrderStatus() );
        orderResponseDTO.setId( order.getId() );

        return orderResponseDTO;
    }
}
