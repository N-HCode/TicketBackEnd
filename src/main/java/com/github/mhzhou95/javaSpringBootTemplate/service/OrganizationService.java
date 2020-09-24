package com.github.mhzhou95.javaSpringBootTemplate.service;

import com.github.mhzhou95.javaSpringBootTemplate.model.Organization;
import com.github.mhzhou95.javaSpringBootTemplate.model.Ticket;
import com.github.mhzhou95.javaSpringBootTemplate.model.User;
import com.github.mhzhou95.javaSpringBootTemplate.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
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
                editableOrg.setDateModified(ZonedDateTime.now());
                organizationRepository.save(editableOrg);
            }else{
                return null;
            }

        return editableOrg;
    }

    public Organization editOrgAddress(Long id, Organization newOrgInfo) {

        Organization editableOrg = this.findById(id);

        editableOrg.setForeignAddress(newOrgInfo.isForeignAddress());
        editableOrg.setOrganizationName(newOrgInfo.getOrganizationName());
        editableOrg.setDateModified(ZonedDateTime.now());
        if(newOrgInfo.isForeignAddress()){
            editableOrg.setCity(newOrgInfo.getCity());
            editableOrg.setState("");
            editableOrg.setStreetAddress(newOrgInfo.getStreetAddress());
            editableOrg.setCountry(newOrgInfo.getCountry());
            editableOrg.setZipcode("");
        }else{
            editableOrg.setCity(newOrgInfo.getCity());
            editableOrg.setState(newOrgInfo.getState());
            editableOrg.setStreetAddress(newOrgInfo.getStreetAddress());
            editableOrg.setCountry(newOrgInfo.getCountry());
            editableOrg.setZipcode(newOrgInfo.getZipcode());
        }

        organizationRepository.save(editableOrg);
        return editableOrg;
    }

    public Set<User> addUserToOrgContacts(Organization org, User user){
        Set<User> orgContacts = org.getContacts();
        orgContacts.add(user);
        return orgContacts;
    }

    public Set<Ticket> addTicketToOrgCases(Organization org, Ticket ticket){
        Set<Ticket> orgTickets  = org.getAllUsersTickets();
        orgTickets.add(ticket);
        return orgTickets;
    }

}
