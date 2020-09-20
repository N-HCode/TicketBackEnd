package com.github.mhzhou95.javaSpringBootTemplate.repository;

import com.github.mhzhou95.javaSpringBootTemplate.model.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {
}

