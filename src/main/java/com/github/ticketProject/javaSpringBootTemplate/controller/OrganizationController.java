package com.github.ticketProject.javaSpringBootTemplate.controller;

import com.github.ticketProject.javaSpringBootTemplate.model.Organization;
import com.github.ticketProject.javaSpringBootTemplate.model.User;
import com.github.ticketProject.javaSpringBootTemplate.service.OrganizationService;
import com.github.ticketProject.javaSpringBootTemplate.service.PriorityListService;
import com.github.ticketProject.javaSpringBootTemplate.service.StatusListService;
import com.github.ticketProject.javaSpringBootTemplate.service.UserService;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.github.ticketProject.javaSpringBootTemplate.authorization.Permissions.EVERYTHING;

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


//https://stackoverflow.com/questions/778203/are-there-any-naming-convention-guidelines-for-rest-apis
    @CrossOrigin
    @GetMapping("/get_user_organization")
    @PreAuthorize("hasAnyAuthority('everything', 'user:read')")
    public ResponseEntity<?> getUserOrganization(Authentication authResult){

        User user = userService.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user.getUsersList().getOrganization(), HttpStatus.OK);
    }



    @CrossOrigin
    @GetMapping("/get_users_from_organization/{pageNo}/{numberPerPage}")
    @PreAuthorize("hasAnyAuthority('everything', 'user:modify')")
//    @PreAuthorize("hasAuthority('everything', 'user:modify')")
//    @PostAuthorize() //this check the role and permission AFTER the method is done.
//    PreAuthorize checks before.
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ROOT')")
//    HasRole(), hasAnyAuthority()

    //https://stackoverflow.com/questions/32434058/how-to-implement-pagination-in-spring-boot-with-hibernate
    public ResponseEntity<?> getusersfromOrgization(Authentication authResult, @PathVariable int pageNo, @PathVariable int numberPerPage){
        //PageNo 0 is the first page.
        User user = userService.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Page<User> pagedUsers = service.getUsersFromOrganization(user.getUsersList(), pageNo, numberPerPage);

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
    @PreAuthorize("hasAnyAuthority('everything', 'user:delete')")
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
    @PreAuthorize("hasAnyAuthority('everything', 'user:modify')")
    public ResponseEntity<?> editOrganization(@PathVariable Long id, @RequestBody Organization newOrgInfo){

        if (newOrgInfo != null){
            Organization editedOrg= service.editOrganization(id, newOrgInfo);
            return new ResponseEntity<>(editedOrg, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("New Organization not valid", HttpStatus.BAD_REQUEST);
        }

    }


    @CrossOrigin
    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyAuthority('everything', 'user:read')")
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
