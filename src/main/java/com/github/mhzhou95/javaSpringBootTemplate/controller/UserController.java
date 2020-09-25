package com.github.mhzhou95.javaSpringBootTemplate.controller;

import com.github.mhzhou95.javaSpringBootTemplate.model.Organization;
import com.github.mhzhou95.javaSpringBootTemplate.model.Ticket;
import com.github.mhzhou95.javaSpringBootTemplate.model.User;
import com.github.mhzhou95.javaSpringBootTemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
        // Call the service to invoke the findAll method which returns a Iterable
        Iterable<User> allUser = service.findAll();
        // Return the HTTP response with the Iterable, it will be an empty array if no users created
        return new ResponseEntity<>(allUser, HttpStatus.OK);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        // Call the service to delete the User using Id
        User responseDeleteUser = service.delete(id);

        // check if the User was sent back from service to see if it passed
        if (responseDeleteUser != null){
            // response to send back if success
            return new ResponseEntity<>(responseDeleteUser, HttpStatus.OK);
        }else{
            // response to send back if failure
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

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

    @GetMapping("/login")
    public ResponseEntity<?> LoginUser(@RequestParam String username, @RequestParam String password){
        // call the service to get a User back using the username and password
        User responseLoginUser = service.loginUser(username, password);

        // check if the user was sent back from service to see if it passed
        if(responseLoginUser != null ) {
            // response to send back if success
            return new ResponseEntity<>(responseLoginUser, HttpStatus.OK);
        }else{
            // response to send back if failure
            return new ResponseEntity<>("Login failed",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/ticket")
    public ResponseEntity<?> addTicketToUser(@RequestParam Long id,@RequestBody Ticket ticketToAddToUser){
        // call the service to look for the user and then add the ticket to the user
        Ticket responseAddTicket = service.addTicketToUser(id, ticketToAddToUser);
        // check if the ticket was sent back from service to see if it passed
        if( responseAddTicket != null) {
            // response to send back if success
            return new ResponseEntity<>(responseAddTicket, HttpStatus.OK);
        }else{
            // response to send back if failure
            return new ResponseEntity<>("Failed to add ticket to User",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/organization")
    public ResponseEntity<?> addOrganizationToUser(@RequestParam Long id, @RequestBody Organization organization){
        // call the service to add the organization to the user
        Organization responseAddOrganization = service.addOrganizationToUser(id, organization);

        // check if the organization was sent back from service to see if it passed
        if(responseAddOrganization != null){
            // response to send back if success
            return new ResponseEntity<>(responseAddOrganization, HttpStatus.OK);
        }else{
            // response to send back if failure
            return new ResponseEntity<>("Failed to add organization to User",HttpStatus.BAD_REQUEST);
        }
    }
}
