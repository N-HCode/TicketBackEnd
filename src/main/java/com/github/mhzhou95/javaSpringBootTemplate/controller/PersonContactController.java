package com.github.mhzhou95.javaSpringBootTemplate.controller;

import com.github.mhzhou95.javaSpringBootTemplate.service.PersonContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PersonContactController {

    PersonContactService personContactService;

    @Autowired
    public PersonContactController(PersonContactService personContactService) {
        this.personContactService = personContactService;
    }
}
