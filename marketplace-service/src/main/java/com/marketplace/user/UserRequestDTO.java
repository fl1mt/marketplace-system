package com.marketplace.user;
import jakarta.validation.constraints.NotBlank;
public class UserRequestDTO {
    @NotBlank(message = "Phone number is required!")
    private String phoneNumber;
    @NotBlank(message = "Firstname is required!")
    private String firstname;
    private String lastname;
    @NotBlank(message = "Email is required!")
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
