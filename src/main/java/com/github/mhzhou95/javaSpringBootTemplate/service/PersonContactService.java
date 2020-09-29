package com.github.mhzhou95.javaSpringBootTemplate.service;

import com.github.mhzhou95.javaSpringBootTemplate.model.Organization;
import com.github.mhzhou95.javaSpringBootTemplate.model.PersonContact;
import com.github.mhzhou95.javaSpringBootTemplate.model.Ticket;
import com.github.mhzhou95.javaSpringBootTemplate.model.User;
import com.github.mhzhou95.javaSpringBootTemplate.repository.PersonContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class PersonContactService {

    private PersonContactRepository personContactRepository;

    @Autowired
    public PersonContactService(PersonContactRepository personContactRepository) {
        this.personContactRepository = personContactRepository;
    }

    public Iterable<PersonContact> findAll()
    {
        return personContactRepository.findAll();
    }

    public PersonContact findById(Long id) {
        //Optional are used to avoid null exception.
        //It is just a container object which may or may not contain a non-null value
        return personContactRepository.findById(id).orElse(null);

    }

    public PersonContact createPersonContact(PersonContact personContact)
    {
        //make sure the body is not null
        if (personContact != null){
            return personContactRepository.save(personContact);
        } else {
            return null;
        }
    }

    public PersonContact delete(Long id) {
        PersonContact personContact = this.findById(id);

        if (personContact != null){
            personContactRepository.deleteById(id);
        }

        return personContact;

    }


    public PersonContact editPersonContact(Long id, PersonContact newPersonInfo) {

        PersonContact editablePersonContact = this.findById(id);

        editablePersonContact.setLastModified(LocalDateTime.now());
        editablePersonContact.setFirstName(newPersonInfo.getFirstName());
        editablePersonContact.setLastName(newPersonInfo.getLastName());
        editablePersonContact.setEmail(newPersonInfo.getEmail());
        editablePersonContact.setPhoneNumber(newPersonInfo.getPhoneNumber());
        editablePersonContact.setOrganizationContact(newPersonInfo.getOrganizationContact());

        personContactRepository.save(editablePersonContact);
        return editablePersonContact;
    }

    public Set<Ticket> addTicketToPersonContact(PersonContact personContact, Ticket ticket){
        Set<Ticket> orgTickets  = personContact.getContactsTickets();
        orgTickets.add(ticket);
        return orgTickets;
    }
}
