package com.github.mhzhou95.javaSpringBootTemplate.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private UserDaoService userDaoService;

    @Autowired
    public CustomUserDetailService(@Qualifier("UserDaoService") UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDaoService.selectApplicationUserByUsername(username);
    }
}
