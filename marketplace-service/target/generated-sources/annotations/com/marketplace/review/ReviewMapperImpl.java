package com.marketplace.review;

import com.marketplace.user.UserMapper;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-09T03:39:12+0500",
    comments = "version: 1.6.2, compiler: javac, environment: Java 24.0.2 (Amazon.com Inc.)"
)
@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Review toEntity(ReviewRequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        Review review = new Review();

        review.setRating( requestDTO.getRating() );
        review.setDescription( requestDTO.getDescription() );

        return review;
    }

    @Override
    public ReviewResponseDTO toResponseDTO(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO();

        reviewResponseDTO.setUserPublicDTO( userMapper.toPublicDTO( review.getUser() ) );
        reviewResponseDTO.setId( review.getId() );
        reviewResponseDTO.setRating( review.getRating() );
        reviewResponseDTO.setDescription( review.getDescription() );
        reviewResponseDTO.setReviewDate( review.getReviewDate() );

        return reviewResponseDTO;
    }

    @Override
    public List<ReviewResponseDTO> toResponseDtoList(List<Review> reviews) {
        if ( reviews == null ) {
            return null;
        }

        List<ReviewResponseDTO> list = new ArrayList<ReviewResponseDTO>( reviews.size() );
        for ( Review review : reviews ) {
            list.add( toResponseDTO( review ) );
        }

        return list;
    }
}
