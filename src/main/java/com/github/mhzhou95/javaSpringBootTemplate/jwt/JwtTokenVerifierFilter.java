package com.github.mhzhou95.javaSpringBootTemplate.jwt;

import com.google.common.base.Strings;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
//        String token = cookieAuthorization.replace( jwtConfig.getTokenPrefix(),"" )

        //Tried to add Bearer with a space to the token. However, the cookie could not process
        //the space. Therefore, just added Bearer without the space and just use substring to get
        // the token out.
        String token = cookieAuthorization.substring(jwtConfig.getTokenPrefix().length());

        try {

            //If the token is expired it will fail here when it is parsing the key.
            Jws<Claims> claimsJwt = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            Claims body = claimsJwt.getBody();

            String username = body.getSubject();

            List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");

            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                    .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                    .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username, null, simpleGrantedAuthorities
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(httpServletRequest, httpServletResponse);

        }
        //(JwtException e) is a catch all. you can alt enter to get the detailed ones.
        catch (ExpiredJwtException e) {

            //We created a refresh token that only sends to the /refresh endpoint.
            //So we if if the cookie is not empty from the request then we are going
            //to the refresh endpoint and we should just continue the filter chain.
            //otherwise we can just throw the expired token

            //we can also check to see if the endpoint we are working with does not matter
            //if the token is expired or not.
            //we put the organization/create as an edge case. If they have an expired token
            //while they are trying to create they will get a 403 forbidden.
            //But we want anyone to be able to create an organization.
            //We don't want to completely disable filters as they would reduce security

            if(httpServletRequest.getRequestURI().equals("/refresh") || httpServletRequest.getRequestURI().equals("/organization/create")){
                filterChain.doFilter(httpServletRequest, httpServletResponse);

            }else{
                throw new IllegalStateException("Expired Token");
            }



        } catch (MalformedJwtException e) {

            throw new MalformedJwtException(e.getMessage());

        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException(e.getMessage());

        } catch (SignatureException e) {
            throw new SignatureException(e.getMessage());

        }
        //if we did not set an Authentication. It will be forbidden if the user's token is expired.



    }
}
