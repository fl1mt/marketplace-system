package com.marketplace.mapper;

import com.marketplace.dto.UserPublicDTO;
import com.marketplace.dto.UserRequestDTO;
import com.marketplace.dto.UserResponseDTO;
import com.marketplace.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDTO requestDTO);
    UserPublicDTO toPublicDTO(User user);
    UserResponseDTO toResponseDTO(User user);
}
