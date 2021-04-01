package com.github.ticketProject.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
@Table(name = "ticket_column_template")
public class TicketColumnTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private TicketColumnTemplateList ticketColumnTemplateList;

    private String templateName;



    public TicketColumnTemplate() {
    }

    public TicketColumnTemplate(TicketColumnTemplateList ticketColumnTemplateList) {
        this.ticketColumnTemplateList = ticketColumnTemplateList;
    }

    public Long getId() {
        return id;
    }

    public TicketColumnTemplateList getTicketColumnTemplateList() {
        return ticketColumnTemplateList;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
