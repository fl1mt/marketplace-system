package com.marketplace.user;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-06T05:53:41+0500",
    comments = "version: 1.6.2, compiler: javac, environment: Java 24.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserRequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        User user = new User();

        user.setFirstname( requestDTO.getFirstname() );
        user.setLastname( requestDTO.getLastname() );
        user.setEmail( requestDTO.getEmail() );
        user.setPhoneNumber( requestDTO.getPhoneNumber() );

        return user;
    }

    @Override
    public UserPublicDTO toPublicDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserPublicDTO userPublicDTO = new UserPublicDTO();

        userPublicDTO.setId( user.getId() );
        userPublicDTO.setFirstname( user.getFirstname() );

        return userPublicDTO;
    }

    @Override
    public UserResponseDTO toResponseDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setId( user.getId() );
        userResponseDTO.setPhoneNumber( user.getPhoneNumber() );
        userResponseDTO.setFirstname( user.getFirstname() );
        userResponseDTO.setLastname( user.getLastname() );
        userResponseDTO.setEmail( user.getEmail() );
        userResponseDTO.setCreatedAt( user.getCreatedAt() );
        userResponseDTO.setUpdatedAt( user.getUpdatedAt() );

        return userResponseDTO;
    }
}
