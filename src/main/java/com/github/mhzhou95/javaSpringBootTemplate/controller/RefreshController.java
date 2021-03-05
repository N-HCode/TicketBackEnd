package com.github.mhzhou95.javaSpringBootTemplate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/refresh")
public class RefreshController {

    @GetMapping
    //Use the HttpServletRequest to get the response
    public ResponseEntity<?> refresh(HttpServletRequest request){

        return new ResponseEntity<>(request.getCookies(), HttpStatus.OK);

    }
}
