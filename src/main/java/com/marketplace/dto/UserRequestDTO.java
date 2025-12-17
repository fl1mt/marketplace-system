package com.marketplace.dto;

import jakarta.validation.constraints.NotNull;

public class UserRequestDTO {
    @NotNull(message = "Phone number is required!")
    private String phoneNumber;
    @NotNull(message = "Firstname is required!")
    private String firstname;
    private String lastname;
    @NotNull(message = "Email is required!")
    private String email;

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
