package com.github.mhzhou95.javaSpringBootTemplate.controller;

import com.github.mhzhou95.javaSpringBootTemplate.model.Organization;
import com.github.mhzhou95.javaSpringBootTemplate.model.User;
import com.github.mhzhou95.javaSpringBootTemplate.service.OrganizationService;
import com.github.mhzhou95.javaSpringBootTemplate.service.UserService;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}/edit-name")
    public ResponseEntity<?> editOrg(@PathVariable Long id, @RequestBody String name){

        Organization editableOrg = service.findById(id);

        if(editableOrg != null){
            if (name != null && !name.equals("")){
                editableOrg = service.editOrgName(editableOrg, name);
                return new ResponseEntity<>(editableOrg, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Name not valid", HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>("Organization not found", HttpStatus.NOT_FOUND);
        }



    }

    @PutMapping("/{id}/add-user")
    public ResponseEntity<?> editOrg(@PathVariable Long id, @RequestBody Long userId){

        Organization editableOrg = service.findById(id);
        if(editableOrg != null){
            User user = userService.findById(userId);
            if (user != null){
                Set<User> newOrgContacts= service.addUsertoOrgContacts(editableOrg, user);
                return new ResponseEntity<>(newOrgContacts, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

        }else{
            return new ResponseEntity<>("Organization not found", HttpStatus.NOT_FOUND);
        }


    }

}
