package com.github.ticketProject.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


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

    @ElementCollection
    private List<String> columnNames = new ArrayList<>();

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

    public void addColumnName(String columnName){
        columnNames.add(columnName);
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }
}
