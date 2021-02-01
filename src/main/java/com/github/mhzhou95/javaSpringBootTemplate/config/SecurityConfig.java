package com.github.mhzhou95.javaSpringBootTemplate.config;

import com.github.mhzhou95.javaSpringBootTemplate.auth.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //This allows us to use annotations for authorization on the Controllers
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private PasswordEncoder passwordEncoder;
    private CustomUserDetailService customUserDetailService;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, CustomUserDetailService customUserDetailService) {
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailService = customUserDetailService;
    }

    //This may be needed in case we do form authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/organization/create").permitAll()
//                    .antMatchers("/user/login").permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .formLogin()
//                    .loginPage("/login").permitAll()
//                    .defaultSuccessUrl("/swagger-ui/#")
                    .and()
                    .cors( //This is used to configure Cors
                        c -> { // inside is a customizer. a customizer is an interface with only 1 abstract method
                            //since it is a customizer, we can just use a lambda function.

                            CorsConfigurationSource cs = request -> {
                                // if you look in CorsConfigurationSource it has CorsConfiguration
                                // as a field. so we just make it like this.
                                CorsConfiguration cc = new CorsConfiguration();

                                //then we can set the allowed origins
                                cc.setAllowedOrigins(Arrays.asList("*"));
                                //You will also need to set the allowed methods
                                cc.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));

                                return cc;
                            };
                            c.configurationSource(cs);
                        }
                    )
            ;

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
