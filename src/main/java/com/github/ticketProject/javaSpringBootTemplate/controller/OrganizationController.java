package com.github.ticketProject.javaSpringBootTemplate.controller;

import com.github.ticketProject.javaSpringBootTemplate.model.Organization;
import com.github.ticketProject.javaSpringBootTemplate.model.User;
import com.github.ticketProject.javaSpringBootTemplate.service.OrganizationService;
import com.github.ticketProject.javaSpringBootTemplate.service.PriorityListService;
import com.github.ticketProject.javaSpringBootTemplate.service.StatusListService;
import com.github.ticketProject.javaSpringBootTemplate.service.UserService;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

//Added Swagger documentation. Can be viewed at http://localhost:8080/swagger-ui/#/

@Controller
@RequestMapping(value = "/organization")
public class OrganizationController {
    private OrganizationService service;
    private UserService userService;
    private StatusListService statusListService;
    private PriorityListService priorityListService;

    @Autowired
    public OrganizationController(OrganizationService service,
                                  UserService userService,
                                  StatusListService statusListService,
                                  PriorityListService priorityListService) {
        this.service = service;
        this.userService = userService;
        this.statusListService = statusListService;
        this.priorityListService = priorityListService;
    }

    @CrossOrigin
    @GetMapping("/all")
    public ResponseEntity<?> findAll(){
        //Doing a find all will just return an OK because even if it is empty
        //that is find. As we are not trying to find something specific.
        Iterable<Organization> allOrganizations = service.findAll();
        return new ResponseEntity<>(allOrganizations, HttpStatus.OK);
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

    @CrossOrigin
    @GetMapping("/getuserorganization")
    public ResponseEntity<?> getUserOrganization(Authentication authResult){

        User user = userService.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user.getUsersList().getOrganization(), HttpStatus.OK);
    }


    @CrossOrigin
    @GetMapping("/getAllUser/{id}")
    public ResponseEntity<?> getAllUsersInOrg(@PathVariable Long id){

        //services.findById will return a null if it does not find a
        //org with the Id
        Organization organization = service.findById(id);
        //initialize the HTTP response
        ResponseEntity<?> responseFindId;

        //See if there is a value other than null. If not, send back a 404 error.
        if (organization != null){
            responseFindId = new ResponseEntity<>(organization.getUsersList().getUsers(), HttpStatus.OK);
        }else{
            responseFindId = new ResponseEntity<>("Organization not found",HttpStatus.NOT_FOUND);
        }
        return responseFindId;
    }

    @CrossOrigin
    @GetMapping("/getusersfromorganization")
    //https://stackoverflow.com/questions/32434058/how-to-implement-pagination-in-spring-boot-with-hibernate
    public ResponseEntity<?> getusersfromOrgization(Authentication authResult
//                                                    @RequestBody @PageableDefault(value=10, page=0) Pageable pageable
    ){

        User user = userService.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Pageable pagingpage = PageRequest.of(0, 10);

        List<User> pagedUsers = service.getUsersFromOrganization(user.getUsersList(), pagingpage);

        return new ResponseEntity<>(pagedUsers, HttpStatus.OK);


    }


    @CrossOrigin
    @PostMapping("/create")
    public ResponseEntity<?> createOrg(@RequestParam String username, @RequestParam String password, @RequestBody Organization organization){

        ResponseEntity<?> responseCreateOrg;
        //create org returns the org that it created. If it could not create
        //then something may have happened.
        Organization responseOrg = service.createOrganization(username, password, organization);

        if(responseOrg != null){
            responseCreateOrg = new ResponseEntity<>(responseOrg, HttpStatus.CREATED);
        }else{
            responseCreateOrg = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseCreateOrg;

    }

    @CrossOrigin
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

    @CrossOrigin
    @PutMapping("/{id}/edit-org-info")
    public ResponseEntity<?> editOrganization(@PathVariable Long id, @RequestBody Organization newOrgInfo){

        if (newOrgInfo != null){
            Organization editedOrg= service.editOrganization(id, newOrgInfo);
            return new ResponseEntity<>(editedOrg, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("New Organization not valid", HttpStatus.BAD_REQUEST);
        }

    }

    @CrossOrigin
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

    @CrossOrigin
    @GetMapping("/user/{id}")
    public ResponseEntity<?> findByUserId(@PathVariable Long id){

        //services.findById will return a null if it does not find a
        //org with the Id
        Organization organization = service.findByUserId(id);
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

}
