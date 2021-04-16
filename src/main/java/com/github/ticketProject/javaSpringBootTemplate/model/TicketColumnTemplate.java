package com.github.ticketProject.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    private final Set<String> columnNames = new HashSet<>();

    public TicketColumnTemplate() {
    }


    public Long getId() {
        return id;
    }



    public String getTemplateName() {
        return templateName;
    }


    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public TicketColumnTemplateList getTicketColumnTemplateList() {
        return ticketColumnTemplateList;
    }

    public void setTicketColumnTemplateList(TicketColumnTemplateList ticketColumnTemplateList) {
        this.ticketColumnTemplateList = ticketColumnTemplateList;
    }

    public Set<String> getColumnNames() {
        return columnNames;
    }

    public void addColumnName(String columnName){
        columnNames.add(columnName);
    }
}
