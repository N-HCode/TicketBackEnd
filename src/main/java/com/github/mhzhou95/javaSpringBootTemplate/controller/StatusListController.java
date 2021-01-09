package com.github.mhzhou95.javaSpringBootTemplate.controller;


import com.github.mhzhou95.javaSpringBootTemplate.service.StatusListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "/statuslist")
public class StatusListController {

    private StatusListService statusListService;

    @Autowired
    public StatusListController(StatusListService statusListService) {
        this.statusListService = statusListService;
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<?> getAllStatus(@PathVariable Long id){
        List<String> listOfStatus = statusListService.findStatusListById(id);

        if (listOfStatus != null){
            return new ResponseEntity<>(listOfStatus, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Status List Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @PutMapping("/add/{id}")
    public ResponseEntity<?> addAStatus(@PathVariable Long id, @RequestBody String Status){
        if (statusListService.addToStatusList(id,Status)){
            return new ResponseEntity<>( HttpStatus.OK);
        }else{

            return new ResponseEntity<>("Add Status failed", HttpStatus.BAD_REQUEST);
        }

    }

    @CrossOrigin
    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeAStatus(@PathVariable Long id, @RequestBody String Status){
        if (statusListService.removeFromStatusList(id,Status)){
            return new ResponseEntity<>( HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Failed to Remove Status", HttpStatus.BAD_REQUEST);
        }
    }

}
