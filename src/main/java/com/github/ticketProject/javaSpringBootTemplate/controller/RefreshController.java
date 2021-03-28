package com.github.ticketProject.javaSpringBootTemplate.controller;

import com.github.ticketProject.javaSpringBootTemplate.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/refresh")
public class RefreshController {

    private RefreshTokenService refreshTokenService;

    @Autowired
    public RefreshController(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    @GetMapping
    //Use the HttpServletRequest to get the response
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response){

        if(refreshTokenService.attemptRefresh(request,response)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
