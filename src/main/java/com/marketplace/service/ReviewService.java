package com.marketplace.service;

import com.marketplace.dto.ReviewRequestDTO;
import com.marketplace.dto.ReviewResponseDTO;
import com.marketplace.entity.Product;
import com.marketplace.entity.Review;
import com.marketplace.entity.User;
import com.marketplace.exceptions.DuplicateException;
import com.marketplace.mapper.ReviewMapper;
import com.marketplace.repository.ProductsRepository;
import com.marketplace.repository.ReviewRepository;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductsRepository productsRepository;
    private final ReviewMapper reviewMapper;
    private final DataAuthService dataAuthService;

    public ReviewService(ReviewRepository reviewRepository, ProductsRepository productsRepository,
                         ReviewMapper reviewMapper, DataAuthService dataAuthService) {
        this.reviewRepository = reviewRepository;
        this.productsRepository = productsRepository;
        this.reviewMapper = reviewMapper;
        this.dataAuthService = dataAuthService;
    }

    @Transactional
    public ReviewResponseDTO createProductReview(UUID userId, UUID productId, ReviewRequestDTO reviewRequestDTO){
        User user = dataAuthService.checkUsersId(userId);
        Product product = productsRepository.findByIdForUpdate(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (reviewRepository
                .existsByUserIdAndProductId(userId, productId)) {
            throw new DuplicateException("User already left a review for this product");
        }

        Review review = reviewMapper.toEntity(reviewRequestDTO);
        review.setUser(user);
        review.setProduct(product);
        Review savedReview = reviewRepository.save(review);

        updateProductRating(product, review.getRating());
        return reviewMapper.toResponseDTO(savedReview);
    }

    public List<ReviewResponseDTO> getReviewsByProduct(UUID productId){
        List<Review> reviews = reviewRepository.findAllByProductId(productId);
        return reviewMapper.toResponseDtoList(reviews);
    }

    public void updateProductRating(Product product, Integer newRating){
        long count = product.getReviewCount();

        BigDecimal avg = product.getAverageRating() == null ? BigDecimal.ZERO : product.getAverageRating();
        BigDecimal total = avg.multiply(BigDecimal.valueOf(count));
        BigDecimal newAvg = total.add(BigDecimal.valueOf(newRating)).divide(BigDecimal.valueOf(count + 1), 2, RoundingMode.HALF_UP);

        product.setReviewCount(count + 1);
        product.setAverageRating(newAvg);

    }
}
