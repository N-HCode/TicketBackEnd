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




//    @CrossOrigin
//    @GetMapping("/{id}")
//    public ResponseEntity findById(@PathVariable Long id){
//       Optional<Ticket> ticketById = service.findById(id);
//        if (ticketById.isPresent()){
//            // response to send back if success
//            return new ResponseEntity<>(ticketById, HttpStatus.OK);
//        }else{
//            // response to send back if failure
//            return new ResponseEntity<>("Ticket not found",HttpStatus.NOT_FOUND);
//        }
//    }

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

//    @CrossOrigin
//    @PutMapping("/{id}")
//    public ResponseEntity<?> editTicket(@PathVariable Long id, @RequestBody Ticket ticket){
//        Ticket editedTicket = service.editTicket(id, ticket);
//        ResponseEntity<?> responseEditTicket = new ResponseEntity<>(editedTicket, HttpStatus.OK);
//        return responseEditTicket;
//    }
//
//    @CrossOrigin
//    @PutMapping("/close/{id}")
//    public ResponseEntity<?> closeTicket(@PathVariable Long id){
//
//        ZonedDateTime closedDate = service.closeTicket(id);
//
//        if (closedDate != null){
//            return new ResponseEntity<>(closedDate ,HttpStatus.OK);
//        }else{
//            return new ResponseEntity<>("Ticket Not Found",HttpStatus.NOT_FOUND);
//        }
//    }

}