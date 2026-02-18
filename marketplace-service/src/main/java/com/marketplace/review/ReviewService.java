package com.marketplace.review;

import com.marketplace.product.Product;
import com.marketplace.user.User;
import com.marketplace.errors.BadRequestException;
import com.marketplace.errors.NotFoundException;
import com.marketplace.product.ProductsRepository;
import com.marketplace.user.auth.DataAuthService;
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
        Product product = dataAuthService.checkProduct(productId);

        if (reviewRepository
                .existsByUserIdAndProductId(userId, productId)) {
            throw new BadRequestException("User already left a review for this product");
        }

        Review review = reviewMapper.toEntity(reviewRequestDTO);
        review.setUser(user);
        review.setProduct(product);
        Review savedReview = reviewRepository.save(review);

        updateProductRating(product, review.getRating());
        return reviewMapper.toResponseDTO(savedReview);
    }

    public List<ReviewResponseDTO> getReviewsByProduct(UUID productId){
        Product product = dataAuthService.checkProduct(productId);

        List<Review> reviews = reviewRepository.findAllByProductId(product.getId());
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
