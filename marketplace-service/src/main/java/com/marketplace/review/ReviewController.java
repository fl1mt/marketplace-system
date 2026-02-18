package com.marketplace.review;

import com.marketplace.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products/{productId}/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByProduct(@PathVariable("productId") UUID productId){
        return ResponseEntity.ok(reviewService.getReviewsByProduct(productId));
    }
    @PostMapping
    public ResponseEntity<ReviewResponseDTO> createReview(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                          @PathVariable("productId") UUID productId,
                                                          @RequestBody @Valid ReviewRequestDTO reviewRequestDTO){
        return ResponseEntity.ok(reviewService.createProductReview(userDetails.getId(), productId, reviewRequestDTO));
    }
}
