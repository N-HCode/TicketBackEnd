package com.github.mhzhou95.javaSpringBootTemplate.controller;

import com.github.mhzhou95.javaSpringBootTemplate.model.Organization;
import com.github.mhzhou95.javaSpringBootTemplate.model.Ticket;
import com.github.mhzhou95.javaSpringBootTemplate.model.User;
import com.github.mhzhou95.javaSpringBootTemplate.service.OrganizationService;
import com.github.mhzhou95.javaSpringBootTemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    private UserService service;
    private OrganizationService organizationService;

    @Autowired
    public UserController(UserService service, OrganizationService organizationService) {
        this.service = service;
        this.organizationService = organizationService;
    }

    @CrossOrigin
    @GetMapping("/")
    public ResponseEntity<?> findAll(){
        // Call the service to invoke the findAll method which returns a Iterable
        Iterable<User> allUsers = service.findAll();
        // Return the HTTP response with the Iterable, it will be an empty array if no users created
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/all")
    public ResponseEntity<?> findByOrg(@RequestBody Organization organization){
        // Call the service to invoke the findAll method which returns a Iterable
        Iterable<User> usersInOrg = service.findByOrg(organization);
        // Return the HTTP response with the Iterable, it will be an empty array if no users created
        return new ResponseEntity<>(usersInOrg, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        // Call the service to invoke findById method
        Optional<User> userById = service.findById(id);
        
        // check if the user was sent back from service to see if it passed
        if (userById.isPresent()){
            // response to send back if success
            return new ResponseEntity<>(userById, HttpStatus.OK);
        }else{
            // response to send back if failure
            return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User userToCreate){
        // Call the service to create the User
        User responseCreateUser = service.createUser(userToCreate);

        // check if the User was sent back from service to see if it passed
        if (responseCreateUser != null){
            // response to send back if success
            return new ResponseEntity<>(responseCreateUser, HttpStatus.CREATED);
        }else{
            // response to send back if failure
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){

        // check if the User was sent back from service to see if it passed
        if (service.delete(id)){
            // response to send back if success
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            // response to send back if failure
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<?> editUser(@PathVariable Long id, @RequestBody User userToEdit){
        // call the service to try to edit the User using id and body
        User responseEditUser = service.editUser(id, userToEdit);

        // check if the user was sent back from service to see if it passed
        if (responseEditUser != null){
            // response to send back if success
            return new ResponseEntity<>(responseEditUser, HttpStatus.OK);
        }else{
            // response to send back if failure
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @CrossOrigin
    @PostMapping("/organization")
    public ResponseEntity<?> addOrganizationToUser(@RequestParam Long id, @RequestParam Long organizationId){
        // call the service to add the organization to the user
        Organization responseAddOrganization = service.addOrganizationToUser(id, organizationId);

        // check if the organization was sent back from service to see if it passed
        if(responseAddOrganization != null){
            // response to send back if success
            return new ResponseEntity<>(responseAddOrganization, HttpStatus.OK);
        }else{
            // response to send back if failure
            return new ResponseEntity<>("Failed to add organization to User",HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @GetMapping("/check/{id}")
    public ResponseEntity check(@PathVariable Long id, @RequestParam String password){
        // Call the service to invoke findById method
        Optional<User> userById = service.findById(id);
        // check if the user was sent back from service to see if it passed
        if (userById.get().getPassword().equals(password)){
            // response to send back if success
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            // response to send back if failure
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @GetMapping("/checkusername")
    public ResponseEntity checkUsername(@RequestParam String username){
        if (!service.checkUsernameIsTaken(username)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @GetMapping("/verify")
    public ResponseEntity verify(){
        //This is just used to see if a client has a cookie with a valid token.
        //if it does it will reach this API and get an 200
        //Otherwise the it will not pass the filters and fail getting a 403 status
        return new ResponseEntity<>( HttpStatus.OK);
    }


}
