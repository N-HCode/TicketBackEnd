package com.github.mhzhou95.javaSpringBootTemplate.controller;

import com.github.mhzhou95.javaSpringBootTemplate.model.OrganizationContact;
import com.github.mhzhou95.javaSpringBootTemplate.model.PersonContact;
import com.github.mhzhou95.javaSpringBootTemplate.model.Ticket;
import com.github.mhzhou95.javaSpringBootTemplate.service.PersonContactService;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
public class PersonContactController {

    PersonContactService service;

    @Autowired
    public PersonContactController(PersonContactService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll(){
        //Doing a find all will just return an OK because even if it is empty
        //that is find. As we are not trying to find something specific.
        Iterable<PersonContact> allPersonContacts = service.findAll();
        return new ResponseEntity<>(allPersonContacts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){

        //services.findById will return a null if it does not find a
        //org with the Id
        PersonContact foundPersonContact = service.findById(id);

        //See if there is a value other than null. If not, send back a 404 error.
        if (foundPersonContact != null){
            return new ResponseEntity<>(foundPersonContact, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Organization not found",HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/create")
    public ResponseEntity<?> createPersonContact(@RequestBody PersonContact personContact){

        //create org returns the org that it created. If it could not create
        //then something may have happened.
        PersonContact responseOrg = service.createPersonContact(personContact);

        if(responseOrg != null){
            return  new ResponseEntity<>(responseOrg,HttpStatus.CREATED);
        }else{
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{id}")
    //Add another response code for the Swagger Documentation
    @ApiResponse(code = 404, message = "Not Found")
    public ResponseEntity<?> deletePersonContact(@PathVariable Long id){

        PersonContact organization = service.delete(id);

        if ( organization != null) {
            return new ResponseEntity<>("Deleted Organization",HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>("Organization not found",HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/{id}/edit-person-contact")
    public ResponseEntity<?> editPersonContact(@PathVariable Long id, @RequestBody PersonContact personContact){

        if (personContact != null){
            PersonContact editedOrg= service.editPersonContact(id, personContact);
            return new ResponseEntity<>(editedOrg, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("New Organization not valid", HttpStatus.BAD_REQUEST);
        }

    }


    @PutMapping("/{id}/add-ticket")
    public ResponseEntity<?> addTicketToOrg(@PathVariable Long id, @RequestBody Ticket ticket){

        PersonContact editablePersonContact = service.findById(id);
        if(editablePersonContact != null){
            if (ticket != null){
                Set<Ticket> newContactTickets = service.addTicketToPersonContact(editablePersonContact, ticket);
                return new ResponseEntity<>(newContactTickets, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Invalid Ticket", HttpStatus.BAD_REQUEST);
            }

        }else{
            return new ResponseEntity<>("Organization not found", HttpStatus.NOT_FOUND);
        }

    }
}
