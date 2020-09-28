package com.github.mhzhou95.javaSpringBootTemplate.service;

import com.github.mhzhou95.javaSpringBootTemplate.model.OrganizationContact;
import com.github.mhzhou95.javaSpringBootTemplate.model.PersonContact;
import com.github.mhzhou95.javaSpringBootTemplate.model.Ticket;

import com.github.mhzhou95.javaSpringBootTemplate.repository.OrganizationContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class OrganizationContactService {

    private OrganizationContactRepository organizationContactRepository;

    @Autowired
    public OrganizationContactService(OrganizationContactRepository organizationContactRepository) {
        this.organizationContactRepository = organizationContactRepository;
    }

    public Iterable<OrganizationContact> findAll()
    {
        return organizationContactRepository.findAll();
    }

    public OrganizationContact findById(Long id) {
        //Optional are used to avoid null exception.
        //It is just a container object which may or may not contain a non-null value
        return organizationContactRepository.findById(id).orElse(null);

    }

    public OrganizationContact createOrganizationContact(OrganizationContact organization)
    {
        //make sure the body is not null
        if (organization != null){
            return organizationContactRepository.save(organization);
        } else {
            return null;
        }
    }

    public OrganizationContact delete(Long id) {
        OrganizationContact organization = this.findById(id);

        if (organization != null){
            organizationContactRepository.deleteById(id);
        }

        return organization;

    }

    public OrganizationContact editOrgName(OrganizationContact editableOrg, String name) {
        if(editableOrg != null){
            editableOrg.setOrganizationContactName(name);
            editableOrg.setDateModified(LocalDateTime.now());
            organizationContactRepository.save(editableOrg);
        }else{
            return null;
        }

        return editableOrg;
    }

    public OrganizationContact editOrgAddress(Long id, OrganizationContact newOrgInfo) {

        OrganizationContact editableOrg = this.findById(id);

        editableOrg.setForeignAddress(newOrgInfo.isForeignAddress());
        editableOrg.setOrganizationContactName(newOrgInfo.getOrganizationContactName());
        editableOrg.setDateModified(LocalDateTime.now());
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

        organizationContactRepository.save(editableOrg);
        return editableOrg;
    }

    public Set<PersonContact> addContactsToOrg(OrganizationContact org, PersonContact contact){
        Set<PersonContact> orgContacts = org.getContacts();
        orgContacts.add(contact);
        return orgContacts;
    }

    public Set<Ticket> addTicketToOrgCases(OrganizationContact org, Ticket ticket){
        Set<Ticket> orgTickets  = org.getAllContactsTickets();
        orgTickets.add(ticket);
        return orgTickets;
    }
}
