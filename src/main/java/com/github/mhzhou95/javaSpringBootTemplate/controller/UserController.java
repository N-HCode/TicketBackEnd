package com.github.mhzhou95.javaSpringBootTemplate.controller;

import com.github.mhzhou95.javaSpringBootTemplate.model.User;
import com.github.mhzhou95.javaSpringBootTemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @GetMapping("all")
    public ResponseEntity<?> findAll(){
        Iterable<User> allUser = service.findAll();
        ResponseEntity<?> responseFindAll = new ResponseEntity<>(allUser, HttpStatus.OK);
        return responseFindAll;
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        // Call the service to invoke findById method
        Optional<User> userById = service.findById(id);
        // Check if the optional contained a User and send the correct HTTP responses
        if (userById.isPresent()){
            return new ResponseEntity<>(userById, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user){
        // Call the service to create the user
        User responseBody = service.createUser(user);
        // If the service accepts the Object it will return the User Object back otherwise null
        if (responseBody != null){
            return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        // Call the service to delete the User
        User responseUser = service.delete(id);
        // If the service accepts the Id it will return the User object back otherwise null
        if (responseUser != null){
            return new ResponseEntity<>(responseUser, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editUser(@PathVariable Long id, @RequestBody User user){
        // call the service to try to get back the user after being edited
        User editedUser = service.editUser(id, user);

        // check if the service returned a user and then respond with the correct HTTP response
        if (editedUser != null){
            return new ResponseEntity<>(editedUser, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
