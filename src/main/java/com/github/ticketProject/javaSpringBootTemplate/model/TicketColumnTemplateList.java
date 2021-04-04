package com.github.ticketProject.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "ticket_column_template_list")
public class TicketColumnTemplateList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="user-ticket_column_template_list")
    private User user;

    public TicketColumnTemplateList() {
    }

    public TicketColumnTemplateList(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }


}
