package com.github.mhzhou95.javaSpringBootTemplate.service;

import com.github.mhzhou95.javaSpringBootTemplate.model.Ticket;
import com.github.mhzhou95.javaSpringBootTemplate.model.User;
import com.github.mhzhou95.javaSpringBootTemplate.repository.TicketRepository;
import com.github.mhzhou95.javaSpringBootTemplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class TicketService {
    private TicketRepository ticketRepository;
    private UserRepository userRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        // create default ticket for testing
//        Ticket defaultTicket = new Ticket("Subject0001", "Description 001", "low");
//        createTicket((long) 1, defaultTicket);
    }

    public Iterable<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Optional<Ticket> findById(Long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        return ticket;
    }

    public Ticket createTicket(Long userId, Ticket ticket) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            User foundUser = user.get();
            ticket.setUser(foundUser);
            ZonedDateTime timeAsOfNow = ZonedDateTime.now();
            ticket.setDateCreated(timeAsOfNow);
            ticket.setLastModified(timeAsOfNow);
            ticket.setAssignedTo(foundUser.getFullName());

            return ticketRepository.save(ticket);
        }
        return null;
    }

    public Ticket delete(Long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        ticketRepository.deleteById(id);
        return ticket.get();
    }

    public Ticket editTicket(Long id, Ticket ticket) {

        // Check the optional to see if anything is present then get the user object out else break out of this method
        Optional<Ticket> findTicket = this.findById(id);

        // Check the optional to see if anything is present then get the user object out else break out of this method
        if (findTicket.isPresent()) {
            Ticket foundTicket = findTicket.get();
            foundTicket.setSubject(ticket.getSubject());
            foundTicket.setDescription(ticket.getDescription());
            foundTicket.setPriority(ticket.getPriority());
            foundTicket.setStatus(ticket.getStatus());
            
            foundTicket.setAssignedTo(ticket.getAssignedTo());
            User newAssignedTo = userRepository.findByFullNameEquals(ticket.getAssignedTo());
            foundTicket.setUser(newAssignedTo);
            return ticketRepository.save(foundTicket);
        } else {
            return null;
        }
    }

//    public User assignedTo(Ticket ticket){
//        return userRepository.findByUserIdIn(ticket);
//    }
}
