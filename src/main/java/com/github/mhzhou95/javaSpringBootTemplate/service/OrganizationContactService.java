package com.github.mhzhou95.javaSpringBootTemplate.service;

import com.github.mhzhou95.javaSpringBootTemplate.repository.OrganizationContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationContactService {

    private OrganizationContactRepository organizationContactRepository;

    @Autowired
    public OrganizationContactService(OrganizationContactRepository organizationContactRepository) {
        this.organizationContactRepository = organizationContactRepository;
    }
}
