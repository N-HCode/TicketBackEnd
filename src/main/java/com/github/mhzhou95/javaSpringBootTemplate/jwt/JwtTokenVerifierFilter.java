package com.github.mhzhou95.javaSpringBootTemplate.jwt;

import com.google.common.base.Strings;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

//this is used to verify the token in each request.
public class JwtTokenVerifierFilter extends OncePerRequestFilter {

    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    public JwtTokenVerifierFilter(SecretKey secretKey, JwtConfig jwtConfig) {
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        //This is how we get the Token from the Header. Remember we added the Token Header in out UsernameAndPassword Filter
        //Since we are using a HTTPOnly Cookie now. There won't be an authorization header.
//        String authorizationHeader = httpServletRequest.getHeader(jwtConfig.getAuthorizationHeader());


        String cookieAuthorization;

        if (httpServletRequest.getCookies() != null){

            //There is not a way to just get one cookie from the request. The getCookies return an array of all the cookies
            //in the response. So we need to filter it to get the one cookie that we want.
            cookieAuthorization = Arrays.stream(httpServletRequest.getCookies())
                    .filter(cookie -> jwtConfig.getAuthorizationCookieName().equals(cookie.getName())).findFirst().get().getValue();
        }else{
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }


        //We check whether the Authorization Header value is null or does not start with Bearer.
        //In this case we use HTTPOnly cookie, so
        //If this is true, then we will just continue without authorizing so the rest of the code is not reached.
        if (Strings.isNullOrEmpty(cookieAuthorization) || !cookieAuthorization.startsWith(jwtConfig.getTokenPrefix())){
            //this means to continue with the other filters. However, we do this before
            //We set any authorization, so there will be a forbidden status sent back.
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        //Here we remove the "Bearer " by replacing it with an empty string. This will leave us
        //with just the token part, so we can get the data from.
        String token = cookieAuthorization.replace( jwtConfig.getTokenPrefix(),"" );

        try{

            //If the token is expired it will fail here when it is parsering the key.
            Jws<Claims> claimsJwt = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            Claims body = claimsJwt.getBody();

            String username = body.getSubject();

            List<Map<String, String>> authorities = (List<Map<String, String>>)  body.get("authorities");

            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                    .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                    .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username, null, simpleGrantedAuthorities
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }catch (JwtException e){
            throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
