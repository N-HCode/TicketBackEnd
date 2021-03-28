package com.github.ticketProject.javaSpringBootTemplate.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.refreshtoken")
public class RefreshTokenConfig {
    //To make it so users don't have to keep relogining in when the access token expires
    //we will implement a refresh token. It doesn't have to be a JWT, but since we already
    //have JWT set it, it would be easier to implement.
    //Usually, the refresh token would only be sent to an Auth server, which should be
    //separate from the resource server. However, in this project we only have one server.
    //In this way, we will make the cookie only be sent if it goes to a specific path.

    private String refreshSecretKey;
    private String cookieName;
    private Integer refreshTokenExpirationAfterMinutes;

    public String getRefreshSecretKey() {
        return refreshSecretKey;
    }

    public void setRefreshSecretKey(String refreshSecretKey) {
        this.refreshSecretKey = refreshSecretKey;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public Integer getRefreshTokenExpirationAfterMinutes() {
        return refreshTokenExpirationAfterMinutes;
    }

    public void setRefreshTokenExpirationAfterMinutes(Integer refreshTokenExpirationAfterMinutes) {
        this.refreshTokenExpirationAfterMinutes = refreshTokenExpirationAfterMinutes;
    }
}
