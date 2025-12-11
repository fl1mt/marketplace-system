package com.marketplace.dto;

import jakarta.validation.constraints.NotNull;

public class DeliveryAddressRequestDTO {
    @NotNull(message = "City is required!")
    private String city;
    @NotNull(message = "Street is required!")
    private String street;
    @NotNull(message = "House is required!")
    private String house;
    private String apartment;
    @NotNull(message = "Index is required!")
    private String index;

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getHouse() {
        return house;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getApartment() {
        return apartment;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }
}
