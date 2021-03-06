package com.github.mhzhou95.javaSpringBootTemplate.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class RefreshTokenService {


    public boolean attemptRefresh(HttpServletRequest request, HttpServletResponse response){


        return false;
    }

}
