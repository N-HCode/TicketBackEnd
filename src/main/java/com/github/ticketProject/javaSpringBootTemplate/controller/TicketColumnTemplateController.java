package com.github.ticketProject.javaSpringBootTemplate.controller;

import com.github.ticketProject.javaSpringBootTemplate.model.TicketColumnTemplate;
import com.github.ticketProject.javaSpringBootTemplate.model.User;
import com.github.ticketProject.javaSpringBootTemplate.service.TicketColumnTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/ticket_templates")
public class TicketColumnTemplateController {

    private TicketColumnTemplateService ticketColumnTemplateService;


    @Autowired
    public TicketColumnTemplateController(TicketColumnTemplateService ticketColumnTemplateService) {
        this.ticketColumnTemplateService = ticketColumnTemplateService;
    }

    @CrossOrigin
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllTicketTemplates(Authentication authResult){
        Iterable<TicketColumnTemplate> ticketColumnTemplates = ticketColumnTemplateService.findAllByTicketTemplateList(authResult);
        if (ticketColumnTemplates == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ticketColumnTemplates, HttpStatus.OK);

    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getTicketTemplatesById(Authentication authResult, @PathVariable long id){
        TicketColumnTemplate ticketColumnTemplate = ticketColumnTemplateService.findById(authResult, id);
        if (ticketColumnTemplate == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ticketColumnTemplate, HttpStatus.OK);

    }

    @CrossOrigin
    @PostMapping(value = "/create")
    public ResponseEntity<?> createTicketTemplates(Authentication authResult, @RequestBody TicketColumnTemplate ticketColumnTemplate){

         boolean addedStatus = ticketColumnTemplateService.addTicketColumnTemplate(authResult, ticketColumnTemplate);

        if(addedStatus){
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @CrossOrigin
    @PutMapping(value = "/edit")
    public ResponseEntity<?> addColumnToTicketTemplates(Authentication authResult){
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
