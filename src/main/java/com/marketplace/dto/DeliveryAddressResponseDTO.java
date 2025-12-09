package com.marketplace.dto;

import com.marketplace.entity.User;
import jakarta.persistence.*;

import java.util.UUID;

public class DeliveryAddressResponseDTO {

    private UUID id;
    private UserResponseDTO userResponseDTO;
    private String city;
    private String street;
    private String house;
    private String apartment;
    private String index;
}
