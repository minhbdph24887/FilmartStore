package com.example.filmartstore.service;

import com.example.filmartstore.entity.Account;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

public interface JwtService {
    String generateToken(Account detailAccountByEmail, Collection<SimpleGrantedAuthority> authorities);

    String generateRefreshToken(Account detailAccountByEmail, Collection<SimpleGrantedAuthority> authorities);

//    String loginOAuthGoogle(GoogleResponse googleResponse);
}
