package com.github.mhzhou95.javaSpringBootTemplate.controller;

import com.github.mhzhou95.javaSpringBootTemplate.model.Organization;
import com.github.mhzhou95.javaSpringBootTemplate.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/organization")
public class OrganizationController {
    private OrganizationService service;

    @Autowired
    public OrganizationController(OrganizationService service) {
        this.service = service;
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){

        Iterable<Organization> allUser = service.findAll();
        return new ResponseEntity<>(allUser, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        //use OrganizationService Function to find the organization
        return service.findById(id);
    }

    @PostMapping("/create-org")
    public ResponseEntity<?> createUser(@RequestBody Organization organization){
        return createUser(organization);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        //the delete service returns a http response itself.
        return service.delete(id);
    }

    @PutMapping("/{id}/edit-name")
    public ResponseEntity<?> editUser(@PathVariable Long id, @RequestBody String name){
        return service.editOrgName(id, name);
    }
}
