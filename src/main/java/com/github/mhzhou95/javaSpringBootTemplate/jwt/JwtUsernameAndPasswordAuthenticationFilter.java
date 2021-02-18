package com.github.mhzhou95.javaSpringBootTemplate.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

//To get the Token in an API request, you need to request POST to [URL]/Login first
//The body need to be a JSON with the username, and password properties.
//Then you will get the authorization header.
//Then for other request, you will use the token in the Authorization header.

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    //All of this is for when the user send the credentials and then we validate those credentials
    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;


    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager
            , JwtConfig jwtConfig, SecretKey secretKey) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper().readValue(request.getInputStream(),
                    UsernameAndPasswordAuthenticationRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), //principal or username
                    authenticationRequest.getPassword() //the credential

            );

            //this is where we verify if the login is good or not. The authenticationManager will handle that.
            Authentication authenticate = authenticationManager.authenticate(authentication);
            return authenticate;

        }catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        try{
            //current time in milli seconds (1/1000 of a second).
            //We will use this to construct the date and expiration date.
            Long currentTime = System.currentTimeMillis();

            //Create the token
            String token = Jwts.builder()
                    .setSubject(authResult.getName())//Header //will be the username
                    .claim("authorities", authResult.getAuthorities()) //Body
                    .setIssuedAt(new Date(currentTime))
//                    .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays()))) //Date from SQL
                    //it is the 1000 for (1/1000 a sec) * 60 to make it a minute then times how many minutes
                    //Standard appears to be 15 minutes for JWT expiration date.
                    .setExpiration(new Date(currentTime + (1000 * 60 * jwtConfig.getTokenExpirationAfterMinutes()))) //this takes a Java.Util.Date
                    .signWith(secretKey)
                    .compact();

            //add token header to the response.
            response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);

        }catch (Exception e){
            System.out.println(e);
        }




    }
}
