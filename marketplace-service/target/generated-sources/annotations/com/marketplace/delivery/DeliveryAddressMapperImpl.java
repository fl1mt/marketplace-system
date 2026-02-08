package com.marketplace.delivery;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-09T03:39:12+0500",
    comments = "version: 1.6.2, compiler: javac, environment: Java 24.0.2 (Amazon.com Inc.)"
)
@Component
public class DeliveryAddressMapperImpl implements DeliveryAddressMapper {

    @Override
    public DeliveryAddress toEntity(DeliveryAddressRequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        DeliveryAddress deliveryAddress = new DeliveryAddress();

        deliveryAddress.setCity( requestDTO.getCity() );
        deliveryAddress.setStreet( requestDTO.getStreet() );
        deliveryAddress.setHouse( requestDTO.getHouse() );
        deliveryAddress.setApartment( requestDTO.getApartment() );
        deliveryAddress.setIndex( requestDTO.getIndex() );

        return deliveryAddress;
    }

    @Override
    public DeliveryAddressResponseDTO toDeliveryAddressDTO(DeliveryAddress deliveryAddress) {
        if ( deliveryAddress == null ) {
            return null;
        }

        DeliveryAddressResponseDTO deliveryAddressResponseDTO = new DeliveryAddressResponseDTO();

        deliveryAddressResponseDTO.setId( deliveryAddress.getId() );
        deliveryAddressResponseDTO.setCity( deliveryAddress.getCity() );
        deliveryAddressResponseDTO.setStreet( deliveryAddress.getStreet() );
        deliveryAddressResponseDTO.setHouse( deliveryAddress.getHouse() );
        deliveryAddressResponseDTO.setApartment( deliveryAddress.getApartment() );
        deliveryAddressResponseDTO.setIndex( deliveryAddress.getIndex() );

        return deliveryAddressResponseDTO;
    }

    @Override
    public List<DeliveryAddressResponseDTO> toDeliveryAddressesDto(List<DeliveryAddress> deliveryAddresses) {
        if ( deliveryAddresses == null ) {
            return null;
        }

        List<DeliveryAddressResponseDTO> list = new ArrayList<DeliveryAddressResponseDTO>( deliveryAddresses.size() );
        for ( DeliveryAddress deliveryAddress : deliveryAddresses ) {
            list.add( toDeliveryAddressDTO( deliveryAddress ) );
        }

        return list;
    }
}
