package com.github.mhzhou95.javaSpringBootTemplate.controller;

import com.github.mhzhou95.javaSpringBootTemplate.model.*;
import com.github.mhzhou95.javaSpringBootTemplate.service.OrganizationContactService;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@Controller
@RequestMapping(value = "/organizationcontact")
public class OrganizationContactController {

    OrganizationContactService service;

    @Autowired
    public OrganizationContactController(OrganizationContactService organizationContactService) {
        this.service = organizationContactService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll(){
        //Doing a find all will just return an OK because even if it is empty
        //that is find. As we are not trying to find something specific.
        Iterable<OrganizationContact> allOrgs = service.findAll();
        return new ResponseEntity<>(allOrgs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){

        //services.findById will return a null if it does not find a
        //org with the Id
        OrganizationContact organization = service.findById(id);
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
    public ResponseEntity<?> createOrg(@RequestBody OrganizationContact organization){

        ResponseEntity<?> responseCreateOrg;
        //create org returns the org that it created. If it could not create
        //then something may have happened.
        OrganizationContact responseOrg = service.createOrganizationContact(organization);

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

        OrganizationContact organization = service.delete(id);

        if ( organization != null) {
            return new ResponseEntity<>("Deleted Organization",HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>("Organization not found",HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/{id}/edit-org-info")
    public ResponseEntity<?> editOrganizationContact(@PathVariable Long id, @RequestBody OrganizationContact newOrgInfo){

        if (newOrgInfo != null){
            OrganizationContact editedOrg= service.editOrganizationContact(id, newOrgInfo);
            return new ResponseEntity<>(editedOrg, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("New Organization not valid", HttpStatus.BAD_REQUEST);
        }

    }


    @PutMapping("/{id}/add-user")
    public ResponseEntity<?> addContactsToOrg(@PathVariable Long id, @RequestBody PersonContact contact){

        OrganizationContact editableOrg = service.findById(id);
        if(editableOrg != null){
                Set<PersonContact> newOrgContacts= service.addContactsToOrg(editableOrg,contact);
                return new ResponseEntity<>(newOrgContacts, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Organization not found", HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/{id}/add-ticket")
    public ResponseEntity<?> addTicketToOrg(@PathVariable Long id, @RequestBody Ticket ticket){

        OrganizationContact editableOrg = service.findById(id);
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
