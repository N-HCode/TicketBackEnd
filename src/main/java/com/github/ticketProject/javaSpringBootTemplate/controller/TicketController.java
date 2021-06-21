package com.github.ticketProject.javaSpringBootTemplate.controller;

import com.github.ticketProject.javaSpringBootTemplate.model.ClientsOrganization;
import com.github.ticketProject.javaSpringBootTemplate.model.Ticket;
import com.github.ticketProject.javaSpringBootTemplate.model.TicketList;
import com.github.ticketProject.javaSpringBootTemplate.model.User;
import com.github.ticketProject.javaSpringBootTemplate.service.ClientsOrganizationService;
import com.github.ticketProject.javaSpringBootTemplate.service.TicketService;
import com.github.ticketProject.javaSpringBootTemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.time.ZonedDateTime;

@Controller
@RequestMapping(value = "/ticket")
public class TicketController {
    private final TicketService service;
    private final UserService userService;
    private final ClientsOrganizationService clientsOrganizationService;

    @Autowired
    public TicketController(TicketService service,
                            UserService userService,
                            ClientsOrganizationService clientsOrganizationService) {
        this.service = service;
        this.userService = userService;
        this.clientsOrganizationService = clientsOrganizationService;
    }

    @CrossOrigin
    @GetMapping("/all_from_organization/{pageNo}/{numberPerPage}")
    public ResponseEntity<?> findAllFromOrg(Authentication authResult,
                                            @PathVariable int pageNo, @PathVariable int numberPerPage ){

        User user = userService.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Page<Ticket> allTicket = service.findAllTicketsByOrganization(user.getUsersList().getTicketList(), pageNo, numberPerPage);
        return new ResponseEntity<>(allTicket, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/all_from_user/{pageNo}/{numberPerPage}")
    public ResponseEntity<?> findAllFromUser(Authentication authResult,
                                            @PathVariable int pageNo, @PathVariable int numberPerPage ){

        User user = userService.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Page<Ticket> allTicket = service.findAllTicketsByUser(user, pageNo, numberPerPage);
        return new ResponseEntity<>(allTicket, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/all_from_client/{id}/{pageNo}/{numberPerPage}")
    public ResponseEntity<?> findAllFromClientsOrganization(Authentication authResult,@PathVariable long clientOrgid,
                                             @PathVariable int pageNo, @PathVariable int numberPerPage ){

        User user = userService.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        TicketList userTicketList = user.getUsersList().getTicketList();

        ClientsOrganization clientsOrganization = clientsOrganizationService.findClientsOrganizationById(userTicketList.getClientsOrganizationLists(), clientOrgid);
        if (clientsOrganization == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Page<Ticket> allTicket = service.findAllTicketsByClientOrganization(userTicketList,clientsOrganization, pageNo, numberPerPage);
        return new ResponseEntity<>(allTicket, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/search/{pageNo}/{numberPerPage}")
    public ResponseEntity<?> findAllBasedOnStatus(Authentication authResult,
                                                  @PathVariable int pageNo,
                                                  @PathVariable int numberPerPage,
                                                  @RequestParam String status){

        User user = userService.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Page<Ticket> results = service.findByCriteria(authResult,status, pageNo, numberPerPage);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }


    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketById(Authentication authResult, @PathVariable Long id){

        User user = userService.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Ticket ticket = service.findById(user.getUsersList().getTicketList(),id).orElse(null);
        if (ticket == null){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }



    @CrossOrigin
    @PostMapping("/create")
    public ResponseEntity<?> createTicket(Authentication authResult, @RequestBody Ticket ticket){
        if (service.createTicket(authResult, ticket)){
            return new ResponseEntity<>( HttpStatus.CREATED);
        }
        return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
    }

//    @CrossOrigin
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteTicket(@PathVariable Long id){
//        Ticket responseTicket = service.delete(id);
//        ResponseEntity<?> responseDeleteTicket = new ResponseEntity(responseTicket, HttpStatus.OK);
//        return responseDeleteTicket;
//    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<?> editTicket(Authentication authResult,
                                        @PathVariable Long id,
                                        @RequestBody Ticket ticket){

        User user = userService.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Ticket editedTicket = service.editTicket(user.getUsersList().getTicketList(),id, ticket);
        if (editedTicket == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<?> responseEditTicket = new ResponseEntity<>(HttpStatus.OK);
        return responseEditTicket;
    }

    @CrossOrigin
    @PutMapping("/close/{id}")
    public ResponseEntity<?> closeTicket(Authentication authResult, @PathVariable Long id){

        User user = userService.getUserByUsername(authResult.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ZonedDateTime closedDate = service.closeTicket(user.getUsersList().getTicketList(),id);

        if (closedDate != null){
            return new ResponseEntity<>(closedDate ,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Ticket Not Found",HttpStatus.NOT_FOUND);
        }
    }

}