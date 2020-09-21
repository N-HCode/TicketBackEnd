package com.github.mhzhou95.javaSpringBootTemplate.service;

import com.github.mhzhou95.javaSpringBootTemplate.model.Organization;
import com.github.mhzhou95.javaSpringBootTemplate.model.User;
import com.github.mhzhou95.javaSpringBootTemplate.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.Set;

@Service
public class OrganizationService {

    private OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public Iterable<Organization> findAll()
    {
        return organizationRepository.findAll();
    }

    public Organization findById(Long id) {
        //Optional are used to avoid null exception.
        //It is just a container object which may or may not contain a non-null value
        return organizationRepository.findById(id).orElse(null);

    }

    public Organization createOrganization(Organization organization)
    {
        //make sure the body is not null
        if (organization != null){
            return organizationRepository.save(organization);
        } else {
            return null;
        }
    }

    public Organization delete(Long id) {
        Organization organization = this.findById(id);

        if (organization != null){
            organizationRepository.deleteById(id);
        }

        return organization;

    }

    public Organization editOrgName(Organization editableOrg, String name) {
            if(editableOrg != null){
                editableOrg.setOrganizationName(name);
                organizationRepository.save(editableOrg);
            }else{
                return null;
            }

        return editableOrg;
    }

    public Set<User> addUsertoOrgContacts(Organization org, User user){
        Set<User> orgContacts = org.getContacts();
        if(org != null){
            orgContacts.add(user);
        }

        return orgContacts;
    }

}
