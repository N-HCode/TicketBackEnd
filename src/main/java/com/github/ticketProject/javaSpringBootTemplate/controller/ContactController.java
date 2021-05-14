package com.github.ticketProject.javaSpringBootTemplate.controller;

import com.github.ticketProject.javaSpringBootTemplate.model.ClientsOrganization;
import com.github.ticketProject.javaSpringBootTemplate.model.ClientsOrganizationList;
import com.github.ticketProject.javaSpringBootTemplate.model.Contact;
import com.github.ticketProject.javaSpringBootTemplate.model.User;
import com.github.ticketProject.javaSpringBootTemplate.service.ClientsOrganizationService;
import com.github.ticketProject.javaSpringBootTemplate.service.ContactService;
import com.github.ticketProject.javaSpringBootTemplate.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/contact")
public class ContactController {

    private final ContactService contactService;
    private final UserService userService;
    private final ClientsOrganizationService clientsOrganizationService;

    public ContactController(ContactService contactService, UserService userService, ClientsOrganizationService clientsOrganizationService) {
        this.contactService = contactService;
        this.userService = userService;
        this.clientsOrganizationService = clientsOrganizationService;
    }

    @CrossOrigin
    @GetMapping("/all_contact_from_client/{id}/{pageNo}/{numberPerPage}")
    public ResponseEntity<?> findAll(Authentication authResult, @PathVariable int pageNo, @PathVariable int numberPerPage, @PathVariable long id) {
        //Doing a find all will just return an OK because even if it is empty
        //that is find. As we are not trying to find something specific.

        User user = userService.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ClientsOrganizationList clientsOrganizationList = user.getUsersList().getTicketList().getClientsOrganizationLists();

        ClientsOrganization clientsOrganization = clientsOrganizationService.findClientsOrganizationById(clientsOrganizationList, id);
        if (clientsOrganization == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Iterable<Contact> contacts  = contactService.findAllContactByContactList(clientsOrganization.getContactList(),pageNo, numberPerPage);


        return new ResponseEntity<>(contacts, HttpStatus.OK);

    }

    @CrossOrigin
    @GetMapping("/search/{id}/{pageNo}")
    public ResponseEntity<?> searchContactByCriteria(Authentication authResult,@RequestParam String searchTerm,@PathVariable int id,@PathVariable int pageNo){

        User user = userService.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ClientsOrganizationList clientsOrganizationList = user.getUsersList().getTicketList().getClientsOrganizationLists();

        ClientsOrganization clientsOrganization = clientsOrganizationService.findClientsOrganizationById(clientsOrganizationList, id);
        if (clientsOrganization == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Page<Contact> results = contactService.findByCriteria(clientsOrganization, searchTerm, pageNo, 10);
        if (results.isEmpty()){
            return new ResponseEntity<>("No client organization(s) were found based on provided criteria" ,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(results, HttpStatus.OK);

    }


    @CrossOrigin
    @GetMapping("/{organizationId}/{contactId}")
    public ResponseEntity<?> findById(Authentication authResult, @PathVariable long organizationId, @PathVariable long contactId ) {


        User user = userService.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ClientsOrganizationList clientsOrganizationList = user.getUsersList().getTicketList().getClientsOrganizationLists();

        ClientsOrganization clientsOrganization = clientsOrganizationService.findClientsOrganizationById(clientsOrganizationList, organizationId);
        if (clientsOrganization == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Contact contact  = contactService.findContactById(clientsOrganization.getContactList(), contactId);
        if(contact == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(contact, HttpStatus.OK);

    }

    @CrossOrigin
    @PutMapping("remove/{organizationId}/{contactId}")
    public ResponseEntity<?> removeContact(Authentication authResult, @PathVariable long organizationId, @PathVariable long contactId ) {


        User user = userService.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ClientsOrganizationList clientsOrganizationList = user.getUsersList().getTicketList().getClientsOrganizationLists();

        ClientsOrganization clientsOrganization = clientsOrganizationService.findClientsOrganizationById(clientsOrganizationList, organizationId);
        if (clientsOrganization == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Contact contact  = contactService.findContactById(clientsOrganization.getContactList(), contactId);
        if(contact == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(contact, HttpStatus.OK);

    }

    @CrossOrigin
    @PostMapping("create/{organizationId}")
    public ResponseEntity<?> createContact(Authentication authResult, @PathVariable long organizationId,@RequestBody Contact contact) {


        User user = userService.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ClientsOrganizationList clientsOrganizationList = user.getUsersList().getTicketList().getClientsOrganizationLists();

        ClientsOrganization clientsOrganization = clientsOrganizationService.findClientsOrganizationById(clientsOrganizationList, organizationId);
        if (clientsOrganization == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        long id = contactService.createContact(clientsOrganization.getContactList(), contact);

        return new ResponseEntity<>(id, HttpStatus.OK);

    }


}
