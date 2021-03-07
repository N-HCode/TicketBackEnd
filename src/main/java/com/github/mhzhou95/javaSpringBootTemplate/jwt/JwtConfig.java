package com.github.mhzhou95.javaSpringBootTemplate.jwt;

import com.google.common.net.HttpHeaders;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

//We need the configuration annotation, so that a bean is created for this class.
@Configuration
//We will add so that it will relate to the resource folder application.properties
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {

    private String secretKey;
    private String tokenPrefix;
    private String authorizationCookieName;
    private Integer tokenExpirationAfterMinutes;


    public JwtConfig() {
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public Integer getTokenExpirationAfterMinutes() {
        return tokenExpirationAfterMinutes;
    }

    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }

    public String getAuthorizationCookieName() {
        return authorizationCookieName;
    }

//We need these setters because @ConfigurationProperties(prefix = "application.jwt")
    //will use these setter to get the items from the resource and then set the fields.

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public void setTokenExpirationAfterMinutes(Integer tokenExpirationAfterMinutes) {
        this.tokenExpirationAfterMinutes = tokenExpirationAfterMinutes;
    }

    public void setAuthorizationCookieName(String authorizationCookieName) {
        this.authorizationCookieName = authorizationCookieName;
    }
}
