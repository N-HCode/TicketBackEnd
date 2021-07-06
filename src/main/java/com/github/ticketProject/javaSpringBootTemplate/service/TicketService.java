package com.github.ticketProject.javaSpringBootTemplate.service;

import com.github.ticketProject.javaSpringBootTemplate.model.*;
import com.github.ticketProject.javaSpringBootTemplate.repository.TicketRepository;
import com.github.ticketProject.javaSpringBootTemplate.repository.UserRepository;
import com.github.ticketProject.javaSpringBootTemplate.searchUtil.ClientsOrganizationSpecification;
import com.github.ticketProject.javaSpringBootTemplate.searchUtil.SearchCriteria;
import com.github.ticketProject.javaSpringBootTemplate.searchUtil.TicketSpecification;
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
        ticket.setTicketListId(userAssigningTicketTicketList.getId());
        ticket.setDateCreated(ZonedDateTime.now());
        ticket.setLastModified(ZonedDateTime.now());
        ticket.setTicketNumber(userAssigningTicketTicketList.getTotalTicket());
        ticketRepository.save(ticket);
        userAssigningTicketTicketList.setTotalTicket(userAssigningTicketTicketList.getTotalTicket() + 1);
        userRepository.save(userAssigningTicket);

        return true;
    }

    public boolean delete(Authentication authResult,Long id) {

        User user = userService.getUserByUsername(authResult.getName());
        if (user == null ){
            return false;
        }

        TicketList userTicketList = user.getUsersList().getTicketList();

        Optional<Ticket> ticket = ticketRepository.findById(id);

        if (ticket.isPresent() && ticket.get().getTicketList().equals(userTicketList)){
            ticketRepository.deleteById(id);
            userTicketList.setTotalTicket(userTicketList.getTotalTicket() - 1);
            userRepository.save(user);
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
            foundTicket.setLastModified(ZonedDateTime.now());
            foundTicket.setSubject(ticket.getSubject());
            foundTicket.setDescription(ticket.getDescription());
            foundTicket.setResolution(ticket.getResolution());
            foundTicket.setPriority(ticket.getPriority());
            foundTicket.setStatus(ticket.getStatus());
            User newAssignedTo = userRepository.findById(ticket.getUserId()).orElse(null);
            if (newAssignedTo == null) {
                return null;
            }
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

    public Page<Ticket> findByCriteria(Authentication authResult,
                                                    String searchTerm,
                                                    int pageNo,
                                                    int numberPerPage)
    {
        User user = userService.getUserByUsername(authResult.getName());

        //We do this because the rest API will be using a GET. Which does not have a body
        //meaning we can just put query string from the URL.
        //Thus we will create the searchCriteria in the backend instead of having the frontend create a model for it.
        TicketSpecification specification1 =
                new TicketSpecification( new SearchCriteria("status", "~", searchTerm));

        //This spec is to make so that the user only pull data from the organization they are a part of
        TicketSpecification specification2 =
                new TicketSpecification(new SearchCriteria("ticketListId", ":",user.getUsersList().getTicketList().getId()));

        Pageable pageConfig = PageRequest.of(pageNo, numberPerPage);
        //You can use Paging combined with specifications as well.
        Page<Ticket> results = ticketRepository.findAll(Specification.where(specification1).and(specification2), pageConfig);

        return results;
    }
}
