package com.github.ticketProject.javaSpringBootTemplate.controller;

import com.github.ticketProject.javaSpringBootTemplate.model.User;
import com.github.ticketProject.javaSpringBootTemplate.service.PriorityListService;
import com.github.ticketProject.javaSpringBootTemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/priority_list")
public class PriorityListController {

    private final PriorityListService priorityListService;
    private final UserService userService;

    @Autowired
    public PriorityListController(PriorityListService priorityListService, UserService userService) {
        this.priorityListService = priorityListService;
        this.userService = userService;
    }

    @CrossOrigin
    @GetMapping()
    public ResponseEntity<?> getAllPriorities(Authentication authResult){

        User user = userService.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user.getUsersList().getOrganization().getPriorityList(), HttpStatus.OK);

    }

    @CrossOrigin
    @PutMapping("/add")
    public ResponseEntity<?> addAPriority(Authentication authResult, @RequestBody String priority){

        User user = userService.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        if (priorityListService.addToPriorityList(user.getUsersList().getOrganization().getPriorityList(),priority)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{

            return new ResponseEntity<>("Add Status failed", HttpStatus.BAD_REQUEST);
        }

    }

    @CrossOrigin
    @PutMapping("/remove")
    public ResponseEntity<?> removeAPriority(Authentication authResult, @RequestBody String priority){

        User user = userService.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (priorityListService.removeFromPriorityList(user.getUsersList().getOrganization().getPriorityList(),priority)){
            return new ResponseEntity<>( HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Failed to Remove Status", HttpStatus.BAD_REQUEST);
        }
    }

}
