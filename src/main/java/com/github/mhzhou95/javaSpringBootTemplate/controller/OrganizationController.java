package com.github.mhzhou95.javaSpringBootTemplate.controller;


import com.github.mhzhou95.javaSpringBootTemplate.model.Organization;
import com.github.mhzhou95.javaSpringBootTemplate.model.Ticket;
import com.github.mhzhou95.javaSpringBootTemplate.model.User;
import com.github.mhzhou95.javaSpringBootTemplate.service.OrganizationService;
import com.github.mhzhou95.javaSpringBootTemplate.service.UserService;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

//Added Swagger documentation. Can be viewed at http://localhost:8080/swagger-ui/#/

@Controller
@RequestMapping(value = "/organization")
public class OrganizationController {
    private OrganizationService service;
    private UserService userService;

    @Autowired
    public OrganizationController(OrganizationService service, UserService userService) {
        this.service = service;
        this.userService = userService;

    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll(){
        //Doing a find all will just return an OK because even if it is empty
        //that is find. As we are not trying to find something specific.
        Iterable<Organization> allOrgs = service.findAll();
        return new ResponseEntity<>(allOrgs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){

        //services.findById will return a null if it does not find a
        //org with the Id
        Organization organization = service.findById(id);
        //initialize the HTTP response
        ResponseEntity<?> responseFindId;

        //See if there is a value other than null. If not, send back a 404 error.
        if (organization != null){
            responseFindId = new ResponseEntity<>(organization, HttpStatus.OK);
        }else{
            responseFindId = new ResponseEntity<>("Organization not found",HttpStatus.NOT_FOUND);
        }
        return responseFindId;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrg(@RequestBody Organization organization){

        ResponseEntity<?> responseCreateOrg;
        //create org returns the org that it created. If it could not create
        //then something may have happened.
        Organization responseOrg = service.createOrganization(organization);

        if(responseOrg != null){
            responseCreateOrg = new ResponseEntity<>(responseOrg,HttpStatus.CREATED);
        }else{
            responseCreateOrg = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseCreateOrg;

    }

    @DeleteMapping("/{id}")
    //Add another response code for the Swagger Documentation
    @ApiResponse(code = 404, message = "Not Found")
    public ResponseEntity<?> deleteOrg(@PathVariable Long id){

        Organization organization = service.delete(id);

        if ( organization != null) {
            return new ResponseEntity<>("Deleted Organization",HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>("Organization not found",HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/{id}/edit-org-info")
    public ResponseEntity<?> editOrganization(@PathVariable Long id, @RequestBody Organization newOrgInfo){

        if (newOrgInfo != null){
            Organization editedOrg= service.editOrganization(id, newOrgInfo);
            return new ResponseEntity<>(editedOrg, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("New Organization not valid", HttpStatus.BAD_REQUEST);
        }

    }


    @PutMapping("/{id}/add-user")
    public ResponseEntity<?> addUserToOrg(@PathVariable Long id, @RequestBody Long userId){

        Organization editableOrg = service.findById(id);
        if(editableOrg != null){
            Optional<User> user = userService.findById(userId);
            if (user.isPresent()){
                Set<User> newOrgContacts= service.addUserToOrgContacts(editableOrg, user.get());
                return new ResponseEntity<>(newOrgContacts, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

        }else{
            return new ResponseEntity<>("Organization not found", HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/{id}/add-ticket")
    public ResponseEntity<?> addTicketToOrg(@PathVariable Long id, @RequestBody Ticket ticket){

        Organization editableOrg = service.findById(id);
        if(editableOrg != null){
            if (ticket != null){
                Set<Ticket> newOrgTickets= service.addTicketToOrgCases(editableOrg, ticket);
                return new ResponseEntity<>(newOrgTickets, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Invalid Ticket", HttpStatus.BAD_REQUEST);
            }

        }else{
            return new ResponseEntity<>("Organization not found", HttpStatus.NOT_FOUND);
        }

    }


}
