package com.github.ticketProject.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ticket")
public class TicketList {

    @Id
    @Column(name ="ticket_list_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) private long Id;

    @OneToMany(mappedBy = "ticketList", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "ticket_list-ticket")
    private final Set<Ticket> tickets = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "organization-ticket_list")
    private Organization organization;
}
