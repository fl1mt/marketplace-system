package com.marketplace.mapper;

import com.marketplace.dto.ReviewRequestDTO;
import com.marketplace.dto.ReviewResponseDTO;
import com.marketplace.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface ReviewMapper {
    Review toEntity(ReviewRequestDTO requestDTO);
    @Mapping(target = "userPublicDTO", source = "user")
    ReviewResponseDTO toResponseDTO(Review review);
    List<ReviewResponseDTO> toResponseDtoList(List<Review> reviews);
}
