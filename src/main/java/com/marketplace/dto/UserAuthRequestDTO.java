package com.marketplace.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class UserAuthRequestDTO {
    @NotNull(message = "Email is required!")
    private String email;
    @NotNull(message = "Password is required!")
    private String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
