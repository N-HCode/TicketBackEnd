package com.github.ticketProject.javaSpringBootTemplate.controller;

import com.github.ticketProject.javaSpringBootTemplate.model.User;

import com.github.ticketProject.javaSpringBootTemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.github.ticketProject.javaSpringBootTemplate.authorization.Permissions.USER_MODIFY;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }


    @CrossOrigin
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('everything', 'user:read')")
    public ResponseEntity findById(Authentication authResult, @PathVariable Long id){

        User user = service.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // Call the service to invoke findById method
        Optional<User> userById = service.findById(id);
        
        // check if the user was sent back from service to see if it passed
        if (userById.isPresent() && userById.get().getUsersList().equals(user.getUsersList())){
            // response to send back if success
            return new ResponseEntity<>(userById, HttpStatus.OK);
        }else{
            // response to send back if failure
            return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('everything', 'user:create')")
    public ResponseEntity<?> createUser(Authentication authResult, @RequestBody User userToCreate ){


        User user = service.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // check if the User was sent back from service to see if it passed
        if (service.createUser(user.getUsersList(), userToCreate)){
            // response to send back if success
            return new ResponseEntity<>(HttpStatus.CREATED);
        }else{
            // response to send back if failure
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('everything', 'user:delete')")
    public ResponseEntity<?> deleteUser(Authentication authResult, @PathVariable Long id){

        User user = service.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // check if the User was sent back from service to see if it passed
        if (service.delete(user.getUsersList(),id)){
            // response to send back if success
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            // response to send back if failure
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('everything', 'user:modify')")
    public ResponseEntity<?> editUser(Authentication authResult, @PathVariable Long id, @RequestBody User userToEdit){
        User user = service.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // call the service to try to edit the User using id and body
        User responseEditUser = service.editUser(user.getUsersList(),id, userToEdit);

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
    @GetMapping("/check_username")
    public ResponseEntity checkUsername(@RequestParam String username){
        if (!service.checkUsernameIsTaken(username)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @GetMapping("/verify")
    @PreAuthorize("hasAnyAuthority('everything', 'user:read')")
    public ResponseEntity verify(HttpServletRequest request){
        //This is just used to see if a client has a cookie with a valid token.
        //if it does it will reach this API and get an 200
        //Otherwise the it will not pass the filters and fail getting a 403 status
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
