package com.marketplace.dto;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReviewResponseDTO {
    @NotNull(message = "Review ID is required!")
    private UUID id;
    private UserPublicDTO userPublicDTO;
    @NotNull(message = "Review rating is required!")
    private Integer rating;
    private String description;
    private LocalDateTime reviewDate;

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setUserPublicDTO(UserPublicDTO userPublicDTO) {
        this.userPublicDTO = userPublicDTO;
    }

    public UserPublicDTO getUserPublicDTO() {
        return userPublicDTO;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getRating() {
        return rating;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }
}
