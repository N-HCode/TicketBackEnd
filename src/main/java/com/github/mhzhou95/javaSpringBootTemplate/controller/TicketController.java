package com.github.mhzhou95.javaSpringBootTemplate.controller;

import com.github.mhzhou95.javaSpringBootTemplate.model.Ticket;
import com.github.mhzhou95.javaSpringBootTemplate.model.User;
import com.github.mhzhou95.javaSpringBootTemplate.service.TicketService;
import com.github.mhzhou95.javaSpringBootTemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/ticket")
public class TicketController {
    private TicketService service;

    @Autowired
    public TicketController(TicketService service) {
        this.service = service;
    }

    @CrossOrigin
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @GetMapping("/all")
    public ResponseEntity<?> findAll(){
        Iterable<Ticket> allTicket = service.findAll();
        ResponseEntity<?> responseFindAll = new ResponseEntity<>(allTicket, HttpStatus.OK);
        return responseFindAll;
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
       Ticket ticketById = service.findById(id);
        ResponseEntity responseFindId = new ResponseEntity(ticketById, HttpStatus.OK);
        return responseFindId;
    }

    @CrossOrigin
    @PostMapping("/create")
    public ResponseEntity<?> createTicket(@RequestBody Ticket ticket){
        Ticket responseBody = service.createTicket(ticket);
        ResponseEntity<?> responseCreateTicket = new ResponseEntity<>(responseBody, HttpStatus.CREATED);
        return responseCreateTicket;
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        Ticket responseTicket = service.delete(id);
        ResponseEntity<?> responseDeleteTicket = new ResponseEntity(responseTicket, HttpStatus.OK);
        return responseDeleteTicket;
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<?> editUser(@PathVariable Long id, @RequestBody Ticket ticket){
        Ticket editedTicket = service.editTicket(id, ticket);
        ResponseEntity<?> responseEditTicket = new ResponseEntity<>(editedTicket, HttpStatus.OK);
        return responseEditTicket;
    }
}