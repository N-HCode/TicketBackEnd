package com.github.ticketProject.javaSpringBootTemplate.model;

import javax.persistence.*;

@Entity
@Table(name = "ticket_column_template")
public class TicketColumnTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public TicketColumnTemplate() {
    }

    public Long getId() {
        return id;
    }
}
