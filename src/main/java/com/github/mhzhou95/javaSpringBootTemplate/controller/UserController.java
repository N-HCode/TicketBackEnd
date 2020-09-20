package com.github.mhzhou95.javaSpringBootTemplate.controller;

import com.github.mhzhou95.javaSpringBootTemplate.model.User;
import com.github.mhzhou95.javaSpringBootTemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @GetMapping("/")
    public ResponseEntity<?> findAll(){
        Iterable<User> allUser = service.findAll();
        List<User> list = new ArrayList<>();
        allUser.forEach( (user)-> list.add(user));
        ResponseEntity<?> responseFindAll = new ResponseEntity<>(allUser, HttpStatus.OK);
        return responseFindAll;
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        User userById = service.findById(id);
        ResponseEntity responseFindId = new ResponseEntity(userById, HttpStatus.OK);
        return responseFindId;
    }
    @PostMapping("/")
    public ResponseEntity<?> createUser(@RequestBody User user){
        User responseBody = service.createUser(user);
        ResponseEntity<?> responseCreateUser = new ResponseEntity<>(responseBody, HttpStatus.CREATED);
        return responseCreateUser;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        User responseUser = service.delete(id);
        ResponseEntity<?> responseDeleteUser = new ResponseEntity(responseUser, HttpStatus.OK);
        return responseDeleteUser;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editUser(@PathVariable Long id, @RequestBody User user){
        User editedUser = service.editUser(id, user);
        ResponseEntity<?> responseEditUser = new ResponseEntity<>(editedUser, HttpStatus.OK);
        return responseEditUser;
    }
}
