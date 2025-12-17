package com.marketplace.dto;
import java.util.UUID;

public class UserPublicDTO {
    private UUID id;
    private String firstname;
    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }
}
