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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        //Doing a find all will just return an OK because even if it is empty
        //that is find. As we are not trying to find something specific.

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


}
