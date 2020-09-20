package com.github.mhzhou95.javaSpringBootTemplate.service;

import com.github.mhzhou95.javaSpringBootTemplate.model.Ticket;
import com.github.mhzhou95.javaSpringBootTemplate.model.User;
import com.github.mhzhou95.javaSpringBootTemplate.repository.TicketRepository;
import com.github.mhzhou95.javaSpringBootTemplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketService {
    private TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Iterable<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Ticket findById(Long id) {
//        Optional<User> user = userRepository.findById(id);
        Ticket ticket = ticketRepository.findById(id).orElse(new Ticket());
        return ticket;
    }
    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket delete(Long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        ticketRepository.deleteById(id);
        return ticket.get();
    }

    public Ticket editTicket(Long id, Ticket ticket) {
        Ticket optionalTicket = this.findById(id);
        Ticket ticketBefore = optionalTicket;
        // to do logic to check if the right ticket
        return ticketRepository.save(ticketBefore);
    }
}
