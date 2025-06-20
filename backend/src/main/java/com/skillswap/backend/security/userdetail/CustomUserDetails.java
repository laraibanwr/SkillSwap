package com.skillswap.backend.security.userdetail;

import com.skillswap.backend.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Simple role conversion: e.g., "USER" → new SimpleGrantedAuthority("ROLE_USER")
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // hashed password
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // used for login
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // you can change this if you want to expire accounts
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // set to false if user is banned/locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // used for password expiration policies
    }

    @Override
    public boolean isEnabled() {
        return true; // disable user = false
    }

    public User getUser() {
        return user;
    }

}
