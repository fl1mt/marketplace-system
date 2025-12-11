package com.marketplace.service;

import com.marketplace.dto.UserAuthResponseDTO;
import com.marketplace.dto.UserRegisterRequestDTO;
import com.marketplace.dto.UserResponseDTO;
import com.marketplace.entity.User;
import com.marketplace.mapper.UserMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import com.marketplace.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class AuthService {
    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final SecretKey jwtKey;
    @Value("${jwt.expiration}")
    private long expirationTime;

    public AuthService(UserMapper userMapper, UsersRepository userRepository, PasswordEncoder passwordEncoder,
                       @Value("${jwt.secret}") String base64Secret) {
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        byte[] keyBytes = java.util.Base64.getDecoder().decode(base64Secret);
        this.jwtKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public UserAuthResponseDTO login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid data");
        }

        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(jwtKey, SignatureAlgorithm.HS256)
                .compact();

        UserAuthResponseDTO userAuthResponseDTO = new UserAuthResponseDTO();
        userAuthResponseDTO.setToken(token);

        return userAuthResponseDTO;
    }
    @Transactional
    public UserResponseDTO register(UserRegisterRequestDTO request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User with this email already exists");
        }

        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPasswordHash()));
        user.setPhoneNumber(request.getPhoneNumber());
        userRepository.save(user);



        return userMapper.toResponseDTO(user);
    }
}
