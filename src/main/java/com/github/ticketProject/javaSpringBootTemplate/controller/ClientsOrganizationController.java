package com.github.ticketProject.javaSpringBootTemplate.controller;


import com.github.ticketProject.javaSpringBootTemplate.model.ClientsOrganization;
import com.github.ticketProject.javaSpringBootTemplate.model.ClientsOrganizationList;
import com.github.ticketProject.javaSpringBootTemplate.model.User;
import com.github.ticketProject.javaSpringBootTemplate.service.ClientsOrganizationService;
import com.github.ticketProject.javaSpringBootTemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/clients_organization")
public class ClientsOrganizationController {

    private final ClientsOrganizationService clientsOrganizationService;
    private final UserService userService;


    @Autowired
    public ClientsOrganizationController(ClientsOrganizationService clientsOrganizationService, UserService userService) {
        this.clientsOrganizationService = clientsOrganizationService;
        this.userService = userService;
    }

    @CrossOrigin
    @GetMapping("/all_clients")
    public ResponseEntity<?> findAll(Authentication authResult, @PathVariable int pageNo, @PathVariable int numberPerPage) {

        User user = userService.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ClientsOrganizationList clientsOrganizationList = user.getUsersList().getTicketList().getClientsOrganizationLists();

        Iterable<ClientsOrganization> clientOrgList = clientsOrganizationService.findAllClientsOrganizationByClientOrgList(clientsOrganizationList, pageNo, numberPerPage);

        return new ResponseEntity<>(clientOrgList, HttpStatus.OK);

    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<?> findClientsOrganizationById(Authentication authResult, @PathVariable long id){

        User user = userService.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ClientsOrganizationList clientsOrganizationList = user.getUsersList().getTicketList().getClientsOrganizationLists();


        ClientsOrganization clientsOrganization = clientsOrganizationService.findClientsOrganizationById(clientsOrganizationList, id);
        if (clientsOrganization == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(clientsOrganization, HttpStatus.OK);

    }

    @CrossOrigin
    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeById(Authentication authResult, @PathVariable long id){

        User user = userService.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ClientsOrganizationList clientsOrganizationList = user.getUsersList().getTicketList().getClientsOrganizationLists();

        if (clientsOrganizationService.removeClientsOrganization(clientsOrganizationList, id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>( HttpStatus.NOT_FOUND);

    }

    @CrossOrigin
    @PostMapping("/add")
    public ResponseEntity<?> addClientOrganization(Authentication authResult, @RequestBody ClientsOrganization clientsOrganization){

        User user = userService.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ClientsOrganizationList clientsOrganizationList = user.getUsersList().getTicketList().getClientsOrganizationLists();

        if (clientsOrganizationService.addClientsOrganization(clientsOrganizationList, clientsOrganization)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>( HttpStatus.NOT_FOUND);

    }


}
