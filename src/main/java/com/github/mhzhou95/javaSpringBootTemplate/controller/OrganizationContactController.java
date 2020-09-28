package com.github.mhzhou95.javaSpringBootTemplate.controller;

import com.github.mhzhou95.javaSpringBootTemplate.service.OrganizationContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class OrganizationContactController {

    OrganizationContactService organizationContactService;

    @Autowired
    public OrganizationContactController(OrganizationContactService organizationContactService) {
        this.organizationContactService = organizationContactService;
    }
}
