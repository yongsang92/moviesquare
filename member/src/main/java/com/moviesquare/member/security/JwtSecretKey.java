package com.member.www.security;
import javax.crypto.SecretKey;

import com.member.www.properties.JwtConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class JwtSecretKey {

    private final JwtConfig jwtConfig;

    @Bean
    public SecretKey secretKey(){
        return Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes());
    }
}

