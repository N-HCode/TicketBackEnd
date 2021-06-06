package com.github.ticketProject.javaSpringBootTemplate.service;

import com.github.ticketProject.javaSpringBootTemplate.model.*;
import com.github.ticketProject.javaSpringBootTemplate.repository.TicketRepository;
import com.github.ticketProject.javaSpringBootTemplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ClientsOrganizationService clientsOrganizationService;
    private final ContactService contactService;

    @Autowired
    public TicketService(TicketRepository ticketRepository,
                         UserRepository userRepository,
                         UserService userService,
                         ClientsOrganizationService clientsOrganizationService, ContactService contactService) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        // create default ticket for testing
//        Ticket defaultTicket = new Ticket("Subject0001", "Description 001", "low");
//        createTicket((long) 1, defaultTicket);
        this.userService = userService;
        this.clientsOrganizationService = clientsOrganizationService;
        this.contactService = contactService;
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

    public boolean createTicket(Authentication authResult, Ticket ticket) {

        User userAssigningTicket = userService.getUserByUsername(authResult.getName());
        if (userAssigningTicket == null ){
            return false;
        }

        User userAssociatedWithTicket = userRepository.findById(ticket.getUserId()).orElse(null);
        if (userAssociatedWithTicket == null){
            return false;
        }

        TicketList userAssigningTicketTicketList = userAssigningTicket.getUsersList().getTicketList();
        TicketList userAssociatedWithTicketTicketList = userAssociatedWithTicket.getUsersList().getTicketList();

        if (userAssigningTicketTicketList != userAssociatedWithTicketTicketList){
            return false;
        }

        ClientsOrganization clientsOrganization = clientsOrganizationService.findClientsOrganizationById(userAssigningTicket.getUsersList().getOrganization().getClientsOrganizationList(),ticket.getClientsOrganizationId());
        if (clientsOrganization == null){
            return false;
        }

        Contact contact = contactService.findContactById(clientsOrganization.getContactList(), ticket.getContactId());
        if (contact == null) {
            return false;
        }

        ticket.setUser(userAssociatedWithTicket);
        ticket.setClientsOrganization(clientsOrganization);
        ticket.setContact(contact);
        ticket.setTicketList(userAssigningTicketTicketList);
        ticket.setDateCreated(ZonedDateTime.now());
        ticket.setLastModified(ZonedDateTime.now());
        ticketRepository.save(ticket);

        return true;
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
