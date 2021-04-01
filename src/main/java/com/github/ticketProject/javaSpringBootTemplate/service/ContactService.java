package com.github.ticketProject.javaSpringBootTemplate.service;

import com.github.ticketProject.javaSpringBootTemplate.model.Contact;
import com.github.ticketProject.javaSpringBootTemplate.model.ContactList;
import com.github.ticketProject.javaSpringBootTemplate.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Iterable<Contact> findAllContactByContactList(ContactList contactList, int pageNo, int numberPerPage)
    {
        Pageable pageConfig = PageRequest.of(pageNo, numberPerPage);

        return contactRepository.findAllByContactListEquals(contactList, pageConfig).toList();
    }

    public Contact findContactById(ContactList contactList, long id)
    {
         Optional<Contact> contact =  contactRepository.findById(id);

        if (contact.isPresent() && contact.get().getContactList().equals(contactList)){
            return contact.get();
        }

        return null;
    }

    public boolean addContact(ContactList contactList, Contact contact)
    {
        contact.setContactList(contactList);
        contact.setLastModified(ZonedDateTime.now());
        contactRepository.save(contact);

        return true;

    }

    public boolean removeContact(ContactList contactList, long id){

        Contact contact =  findContactById(contactList, id);
        if (contact == null){
            return false;
        }

        contactRepository.delete(contact);

        return true;

    }


}
