package com.github.mhzhou95.javaSpringBootTemplate.config;

import com.github.mhzhou95.javaSpringBootTemplate.auth.CustomUserDetailService;
import com.github.mhzhou95.javaSpringBootTemplate.jwt.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.crypto.SecretKey;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //This allows us to use annotations for authorization on the Controllers
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private PasswordEncoder passwordEncoder;
    private CustomUserDetailService customUserDetailService;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;
    private final RefreshTokenConfig refreshTokenConfig;
    private final RefreshTokenSecretKey refreshTokenSecretKey;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, CustomUserDetailService customUserDetailService, JwtConfig jwtConfig, SecretKey secretKey, RefreshTokenConfig refreshTokenConfig, RefreshTokenSecretKey refreshTokenSecretKey) {
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailService = customUserDetailService;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
        this.refreshTokenConfig = refreshTokenConfig;
        this.refreshTokenSecretKey = refreshTokenSecretKey;
    }

    //This may be needed in case we do form authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    //This is implemented because we are using JWT and JWT is stateless
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    //since we are in something that extends WebSecurityConfigurerAdapter.
                    //there is a parent function that gets the authenticationManager
                    .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey, refreshTokenConfig, refreshTokenSecretKey))
                    .addFilterAfter(new JwtTokenVerifierFilter(secretKey,jwtConfig), JwtUsernameAndPasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers("/organization/create").permitAll()
//                    .antMatchers("/user/login").permitAll()
                    .anyRequest()
                    .authenticated();
//                    .and()
//                    .formLogin();
//                    .loginPage("/login").permitAll()
//                    .defaultSuccessUrl("/swagger-ui/#")

                http.cors( //This is used to configure Cors
                    c -> { // inside is a customizer. a customizer is an interface with only 1 abstract method
                        //since it is a customizer, we can just use a lambda function.

                        CorsConfigurationSource cs = request -> {
                            // if you look in CorsConfigurationSource it has CorsConfiguration
                            // as a field. so we just make it like this.
                            CorsConfiguration cc = new CorsConfiguration();

                            //then we can set the allowed origins
                            cc.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
                            //Need to add the allowed headers as well.
                            cc.setAllowedHeaders(Arrays.asList("x-requested-with", "authorization", "content-type", "Set-Cookie"));
//                            cc.setAllowedHeaders(Arrays.asList("*"));
//                            cc.addExposedHeader("Access-Control-Allow-Credentials");
                            //This will allow the front end to use the withCredentials in the axios configuration
                            cc.setAllowCredentials(true);
                            //You will also need to set the allowed methods
                            cc.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE"));
                            //if you do not put in the Exposed Header then usually the response gotten by the
                            //POST on the frontend, will have a hard time obtaining the header.
                            //Since we are using a HTTPOnly Cookie now. There won't be an authorization header.
//                            cc.addExposedHeader("authorization");

                            return cc;
                        };
                        c.configurationSource(cs);
                    }
                );
    }

    //https://stackoverflow.com/questions/56388865/spring-security-configuration-httpsecurity-vs-websecurity
    //web security impact global security (ignore resources, set debug mode, reject requests by implementing a custom firewall definition)
    //http configuration of web-based security at a resource level, based on a selection match


    @Override
    public void configure(WebSecurity web) throws Exception {
        //this is used to ignore the security filer when doing a certain endpoint.
        //The reason we want to ignore is because we have the JwtRokenVerifier
        //Edge case if they have an expired token still saved and then the JwtTokenVeriferFilter
        //Will reject with a 403 when creating account.
        web.ignoring().antMatchers("/organization/create");
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider () {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(customUserDetailService);
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }




}
