package com.github.ticketProject.javaSpringBootTemplate.controller;

import com.github.ticketProject.javaSpringBootTemplate.service.TicketColumnTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TicketColumnTemplateController {

    private TicketColumnTemplateService ticketColumnTemplateService;


    @Autowired
    public TicketColumnTemplateController(TicketColumnTemplateService ticketColumnTemplateService) {
        this.ticketColumnTemplateService = ticketColumnTemplateService;
    }
}
