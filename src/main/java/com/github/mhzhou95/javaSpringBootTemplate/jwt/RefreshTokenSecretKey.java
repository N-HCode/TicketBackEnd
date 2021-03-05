package com.github.mhzhou95.javaSpringBootTemplate.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class RefreshTokenSecretKey {

    private final RefreshTokenConfig refreshTokenConfig;
    
    @Autowired
    public RefreshTokenSecretKey(RefreshTokenConfig refreshTokenConfig) {
        this.refreshTokenConfig = refreshTokenConfig;
    }

    @Bean(name="refreshToken")
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(refreshTokenConfig.getRefreshSecretKey().getBytes());
    }

}
