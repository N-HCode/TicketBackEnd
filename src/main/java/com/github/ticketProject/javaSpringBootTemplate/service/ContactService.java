package com.github.ticketProject.javaSpringBootTemplate.service;

import com.github.ticketProject.javaSpringBootTemplate.model.ClientsOrganization;
import com.github.ticketProject.javaSpringBootTemplate.model.Contact;
import com.github.ticketProject.javaSpringBootTemplate.model.ContactList;

import com.github.ticketProject.javaSpringBootTemplate.repository.ContactRepository;

import com.github.ticketProject.javaSpringBootTemplate.searchUtil.ContactSpecification;
import com.github.ticketProject.javaSpringBootTemplate.searchUtil.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
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

    public Page<Contact> findByCriteria(ClientsOrganization clientsOrganization, String searchTerm, int pageNo, int numberPerPage
    ){



        //We do this because the rest API will be using a GET. Which does not have a body
        //meaning we can just put query string from the URL.
        //Thus we will create the searchCriteria in the backend instead of having the frontend create a model for it.
        ContactSpecification specification1 =
                new ContactSpecification( new SearchCriteria("firstName", "~", searchTerm));

        //This spec is to make so that the user only pull data from the organization they are a part of
        ContactSpecification specification2 =
                new ContactSpecification(new SearchCriteria("contactListId", ":",clientsOrganization.getContactList().getId()));

//        ClientsOrganizationSpecification specification3 =
//                new ClientsOrganizationSpecification(new SearchCriteria("clientsOrganizationList", ":",user.getUsersList().getTicketList().getClientsOrganizationLists()));

        Pageable pageConfig = PageRequest.of(pageNo, numberPerPage);
        //You can use Paging combined with specifications as well.
        Page<Contact> results = contactRepository.findAll(Specification.where(specification1).and(specification2), pageConfig);

//        List<ClientsOrganization> test = clientsOrganizationRepository.findAll(specification3);

        return results;
    }

    public long createContact(ContactList contactList, Contact contact){

        contact.setContactList(contactList);
        contact.setLastModified(ZonedDateTime.now());
        Contact contact1 = contactRepository.save(contact);
        
        return contact1.getId();
    }


}
