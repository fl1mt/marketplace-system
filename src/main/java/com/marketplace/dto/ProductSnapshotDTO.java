package com.marketplace.dto;

import java.util.UUID;

public class ProductSnapshotDTO {
    private UUID id;
    private String name;

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
