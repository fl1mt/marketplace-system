package com.marketplace.security;

import com.marketplace.entity.User;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CustomUserDetails implements UserDetails {

    private final UUID id;
    private final String email;
    private final String passwordHash;
    private final List<GrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.passwordHash = user.getPasswordHash();

        String role = user.getRole();
        System.out.println(role);

        this.authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())
        );
    }

    public UUID getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return this.passwordHash;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}