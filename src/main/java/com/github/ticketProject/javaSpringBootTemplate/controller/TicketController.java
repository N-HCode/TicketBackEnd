package com.github.ticketProject.javaSpringBootTemplate.controller;

import com.github.ticketProject.javaSpringBootTemplate.model.Ticket;
import com.github.ticketProject.javaSpringBootTemplate.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.Optional;

@Controller
@RequestMapping(value = "/ticket")
public class TicketController {
    private TicketService service;

    @Autowired
    public TicketController(TicketService service) {
        this.service = service;
    }

//    @CrossOrigin
//    @GetMapping("/all")
//    public ResponseEntity<?> findAll(){
//        Iterable<Ticket> allTicket = service.findAll();
//        ResponseEntity<?> responseFindAll = new ResponseEntity<>(allTicket, HttpStatus.OK);
//        return responseFindAll;
//    }

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

//    @CrossOrigin
//    @PostMapping("/create")
//    public ResponseEntity<?> createTicket(@RequestParam Long userId, @RequestBody Ticket ticket){
//        Ticket responseBody = service.createTicket(userId, ticket);
//        ResponseEntity<?> responseCreateTicket = new ResponseEntity<>(responseBody, HttpStatus.CREATED);
//        return responseCreateTicket;
//    }

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