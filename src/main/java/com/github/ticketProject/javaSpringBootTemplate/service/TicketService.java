package com.github.ticketProject.javaSpringBootTemplate.service;

import com.github.ticketProject.javaSpringBootTemplate.model.*;
import com.github.ticketProject.javaSpringBootTemplate.repository.TicketRepository;
import com.github.ticketProject.javaSpringBootTemplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        // create default ticket for testing
//        Ticket defaultTicket = new Ticket("Subject0001", "Description 001", "low");
//        createTicket((long) 1, defaultTicket);
    }

    public Page<Ticket> findAllTicketsByUser(User user, int pageNo, int numberPerPage)
    {
        Pageable pageConfig = PageRequest.of(pageNo, numberPerPage);

        return ticketRepository.findAllByUserEquals(user, pageConfig);
    }

    public Page<Ticket> findAllTicketsByOrganization(TicketList ticketList, int pageNo, int numberPerPage)
    {
        Pageable pageConfig = PageRequest.of(pageNo, numberPerPage);

        return ticketRepository.findAllByTicketListEquals(ticketList, pageConfig);
    }

    public Page<Ticket> findAllTicketsByClientOrganization(TicketList ticketList, ClientsOrganization clientsOrganization, int pageNo, int numberPerPage)
    {

        if (!clientsOrganization.getClientsOrganizationList().getTicketList().equals(ticketList)){
            return null;
        }

        Pageable pageConfig = PageRequest.of(pageNo, numberPerPage);

        return ticketRepository.findAllByClientsOrganizationEquals(clientsOrganization, pageConfig);
    }

    public Page<Ticket> findAllTicketsByContact(TicketList ticketList ,Contact contact , int pageNo, int numberPerPage)
    {

        if (!contact.getContactList().getTicketList().equals(ticketList)){
            return null;
        }

        Pageable pageConfig = PageRequest.of(pageNo, numberPerPage);

        return ticketRepository.findAllByContactEquals(contact, pageConfig);
    }

    public Optional<Ticket> findById(TicketList ticketList,Long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);

        if (ticket.isPresent() && ticket.get().getTicketList().equals(ticketList)){
            return ticket;
        }

        return null;
    }

    public Ticket createTicket(User user, ClientsOrganization clientsOrganization, Contact contact, Ticket ticket) {

        TicketList userTicketList = user.getUsersList().getTicketList();
        TicketList clientOrganizationTicketList = clientsOrganization.getClientsOrganizationList().getTicketList();
        ContactList clientOrganizationContactList = clientsOrganization.getContactList();
        ContactList contactsContactList = contact.getContactList();


        if (userTicketList.equals(clientOrganizationTicketList) &&
                clientOrganizationContactList.equals(contactsContactList)){

            ZonedDateTime timeAsOfNow = ZonedDateTime.now();

            ticket.setTicketList(userTicketList);
            ticket.setUser(user);
            ticket.setClientsOrganization(clientsOrganization);
            ticket.setContact(contact);
            ticket.setDateCreated(timeAsOfNow);
            ticket.setLastModified(timeAsOfNow);

            ticketRepository.save(ticket);

            return ticket;
        }

        return null;
    }

    public boolean delete(TicketList ticketList,Long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);

        if (ticket.isPresent() && ticket.get().getTicketList().equals(ticketList)){
            ticketRepository.deleteById(id);
            return true;
        }
        return false;

    }

    public Ticket editTicket(TicketList ticketList, Long id, Ticket ticket) {

        // Check the optional to see if anything is present then get the user object out else break out of this method
        Optional<Ticket> findTicket = ticketRepository.findById(id);

        // Check the optional to see if anything is present then get the user object out else break out of this method
        if (findTicket.isPresent() && findTicket.get().getTicketList().equals(ticketList)) {
            Ticket foundTicket = findTicket.get();
            foundTicket.setSubject(ticket.getSubject());
            foundTicket.setDescription(ticket.getDescription());
            foundTicket.setResolution(ticket.getResolution());
            foundTicket.setPriority(ticket.getPriority());
            foundTicket.setStatus(ticket.getStatus());
            User newAssignedTo = userRepository.findById(ticket.getUser().getUserId()).orElse(ticket.getUser());
            foundTicket.setUser(newAssignedTo);
            return ticketRepository.save(foundTicket);
        } else {
            return null;
        }
    }

    public ZonedDateTime closeTicket(TicketList ticketList,Long id) {
        // Check the optional to see if anything is present then get the user object out else break out of this method
        Optional<Ticket> findTicket = ticketRepository.findById(id);

        // Check the optional to see if anything is present then get the user object out else break out of this method
        if (findTicket.isPresent()&& findTicket.get().getTicketList().equals(ticketList)) {
            Ticket foundTicket = findTicket.get();
            foundTicket.setDateClosed(ZonedDateTime.now());
            ticketRepository.save(foundTicket);
            return foundTicket.getDateClosed();
        } else {
            return null;
        }
    }
}
