package com.github.ticketProject.javaSpringBootTemplate.controller;


import com.github.ticketProject.javaSpringBootTemplate.model.User;
import com.github.ticketProject.javaSpringBootTemplate.service.StatusListService;
import com.github.ticketProject.javaSpringBootTemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value = "/statuslist")
public class StatusListController {

    private final StatusListService statusListService;
    private final UserService userService;

    @Autowired
    public StatusListController(StatusListService statusListService, UserService userService) {
        this.statusListService = statusListService;
        this.userService = userService;
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<?> getAllStatus(
            Authentication authResult,
            //Authentication authResult we can put this in the parameter and we will get information from the Authentication
            //Like the username or whatever we entered in a JSON token
            @PathVariable Long id){

        User user = userService.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user.getUsersList().getOrganization().getStatusList(), HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/add")
    public ResponseEntity<?> addAStatus(Authentication authResult, @RequestBody String Status){
        User user = userService.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        if (statusListService.addToStatusList(user.getUsersList().getOrganization().getStatusList(),Status)){
            return new ResponseEntity<>( HttpStatus.OK);
        }else{

            return new ResponseEntity<>("Add Status failed", HttpStatus.BAD_REQUEST);
        }

    }

    @CrossOrigin
    @PutMapping("/remove")
    public ResponseEntity<?> removeAStatus(Authentication authResult, @RequestBody String Status){

        User user = userService.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (statusListService.removeFromStatusList(user.getUsersList().getOrganization().getStatusList(),Status)){
            return new ResponseEntity<>( HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Failed to Remove Status", HttpStatus.BAD_REQUEST);
        }
    }

}
