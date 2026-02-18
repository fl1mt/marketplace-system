package com.marketplace.review;
import com.marketplace.user.UserPublicDTO;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReviewResponseDTO {
    private UUID id;
    private UserPublicDTO userPublicDTO;
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
