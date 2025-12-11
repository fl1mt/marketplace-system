package com.marketplace.dto;

import java.util.UUID;

public class DeliveryAddressResponseDTO {

    private UUID id;
    private UserResponseDTO userResponseDTO;
    private String city;
    private String street;
    private String house;
    private String apartment;
    private String index;

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setUserResponseDTO(UserResponseDTO userResponseDTO) {
        this.userResponseDTO = userResponseDTO;
    }

    public UserResponseDTO getUserResponseDTO() {
        return userResponseDTO;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getHouse() {
        return house;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }
}
