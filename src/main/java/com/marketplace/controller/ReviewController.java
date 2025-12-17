package com.marketplace.controller;

import com.marketplace.dto.ReviewRequestDTO;
import com.marketplace.dto.ReviewResponseDTO;
import com.marketplace.security.CustomUserDetails;
import com.marketplace.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/products/{productId}/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByProduct(@PathVariable UUID productId){
        return ResponseEntity.ok(reviewService.getReviewsByProduct(productId));
    }
    @PostMapping
    public ResponseEntity<ReviewResponseDTO> createReview(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                          @PathVariable UUID productId,
                                                          @RequestBody @Valid ReviewRequestDTO reviewRequestDTO){
        return ResponseEntity.ok(reviewService.createProductReview(userDetails.getId(), productId, reviewRequestDTO));
    }
}
