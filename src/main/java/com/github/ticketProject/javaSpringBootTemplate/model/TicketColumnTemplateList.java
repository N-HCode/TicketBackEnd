package com.github.ticketProject.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ticket_column_template_list")
public class TicketColumnTemplateList {


    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="user-ticket_column_template_list")
    private User user;

    public TicketColumnTemplateList() {
    }

    public TicketColumnTemplateList(User user) {
        this.user = user;
    }
}
