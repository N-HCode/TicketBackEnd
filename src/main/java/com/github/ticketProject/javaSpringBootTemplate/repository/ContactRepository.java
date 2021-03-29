package com.github.ticketProject.javaSpringBootTemplate.repository;


import com.github.ticketProject.javaSpringBootTemplate.model.Contact;
import com.github.ticketProject.javaSpringBootTemplate.model.ContactList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContactRepository extends PagingAndSortingRepository<Contact, Long> {

    Page<Contact> findAllByContactListEquals(ContactList contactList, Pageable pageable);

}
