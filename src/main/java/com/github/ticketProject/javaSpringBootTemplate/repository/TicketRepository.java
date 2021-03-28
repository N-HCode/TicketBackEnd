package com.github.ticketProject.javaSpringBootTemplate.repository;

import com.github.ticketProject.javaSpringBootTemplate.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends PagingAndSortingRepository<Ticket, Long> {

    Page<Ticket> findAllByTicketListEquals(TicketList ticketList, Pageable pageable);
    Page<Ticket> findAllByUserEquals(User user, Pageable pageable);
    Page<Ticket> findAllByContactEquals(Contact contact, Pageable pageable);
    Page<Ticket> findAllByClientsOrganizationEquals(ClientsOrganization clientsOrganization, Pageable pageable);

}

