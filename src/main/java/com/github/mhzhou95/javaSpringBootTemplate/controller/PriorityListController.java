package com.github.mhzhou95.javaSpringBootTemplate.controller;

import com.github.mhzhou95.javaSpringBootTemplate.service.PriorityListService;
import com.github.mhzhou95.javaSpringBootTemplate.service.StatusListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/prioritylist")
public class PriorityListController {

    private PriorityListService priorityListService;

    @Autowired
    public PriorityListController(PriorityListService priorityListService) {
        this.priorityListService = priorityListService;
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<?> getAllPriorities(@PathVariable Long id){
        List<String> priorityList = priorityListService.findPriorityListById(id);

        if (priorityList != null){
            return new ResponseEntity<>(priorityList, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Status List Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @PutMapping("/add/{id}")
    public ResponseEntity<?> addAPriority(@PathVariable Long id, @RequestBody String priority){
        if (priorityListService.addToPriorityList(id,priority)){
            return new ResponseEntity<>( HttpStatus.OK);
        }else{

            return new ResponseEntity<>("Add Status failed", HttpStatus.BAD_REQUEST);
        }

    }

    @CrossOrigin
    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeAPriority(@PathVariable Long id, @RequestBody String priority){
        if (priorityListService.removeFromPriorityList(id,priority)){
            return new ResponseEntity<>( HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Failed to Remove Status", HttpStatus.BAD_REQUEST);
        }
    }

}
