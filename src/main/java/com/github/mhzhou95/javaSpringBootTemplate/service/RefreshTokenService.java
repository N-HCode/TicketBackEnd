package com.github.mhzhou95.javaSpringBootTemplate.service;

import com.github.mhzhou95.javaSpringBootTemplate.jwt.JwtConfig;
import com.github.mhzhou95.javaSpringBootTemplate.jwt.RefreshTokenConfig;
import com.google.common.base.Strings;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Service
public class RefreshTokenService {

    private final RefreshTokenConfig refreshTokenConfig;
    private final SecretKey refreshTokenSecretKey;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    @Autowired
    public RefreshTokenService(RefreshTokenConfig refreshTokenConfig, @Qualifier("refreshToken") SecretKey refreshTokenSecretKey, JwtConfig jwtConfig, @Qualifier("JWTToken") SecretKey secretKey) {
        this.refreshTokenConfig = refreshTokenConfig;
        this.refreshTokenSecretKey = refreshTokenSecretKey;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }


    public boolean attemptRefresh(HttpServletRequest request, HttpServletResponse response){

        String refreshToken;
        String expiredToken;

        if (request.getCookies() != null){

            //There is not a way to just get one cookie from the request. The getCookies return an array of all the cookies
            //in the response. So we need to filter it to get the one cookie that we want.
            refreshToken = Arrays.stream(request.getCookies())
                    .filter(cookie -> refreshTokenConfig.getCookieName().equals(cookie.getName())).findFirst().get().getValue();

            expiredToken = Arrays.stream(request.getCookies())
                    .filter(cookie -> jwtConfig.getAuthorizationCookieName().equals(cookie.getName())).findFirst().get().getValue();

        }else {

            return false;
        }

        if (Strings.isNullOrEmpty(refreshToken) || Strings.isNullOrEmpty(expiredToken)){
            return false;
        }

        try {

            String token = expiredToken.substring(jwtConfig.getTokenPrefix().length());

            //If the token is expired it will fail here when it is parsing the key.
            Jws<Claims> claimsJwt = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);

        }
        //(JwtException e) is a catch all. you can alt enter to get the detailed ones.
        catch (ExpiredJwtException e) {

            try{
                //this is parsing the refresh token to see if there is anything wrong with it
                Jws<Claims> claimsJwt = Jwts.parserBuilder().setSigningKey(refreshTokenSecretKey).build().parseClaimsJws(refreshToken);
                Claims refreshBody = claimsJwt.getBody();
                String refreshUsername = refreshBody.getSubject();

                //the error of a JWTException contains the claims as well. So we can get it.
                //This is just used to validate the expired Token header to the Refresh Token header
                //This is so we can know that these token relates to each other instead of the a random token
                //being sent
                if (refreshUsername.equals(e.getClaims().get("sub"))){

                    System.out.println("We did it");
                }

            } catch (ExpiredJwtException error) {

                throw new IllegalStateException("Expired Token");

            }catch (MalformedJwtException error) {

                throw new MalformedJwtException(error.getMessage());

            } catch (UnsupportedJwtException error) {
                throw new UnsupportedJwtException(error.getMessage());

            } catch (SignatureException error) {
                throw new SignatureException(error.getMessage());

            }



        }

        return false;
    }

}
