package com.github.ticketProject.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "priority_list")
public class PriorityList {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long priorityListId;

    //@ElementCollection annotation is used to store a list of values as an entity attribute without needing to model an additional entity
    //@OneToMany as OneToMany is for one-Entity-many-Entity relationship
    @ElementCollection
    private List<String> priorities = new ArrayList<>();

    @OneToOne
    @JsonBackReference(value="organization-priority_list")
    private Organization organization;

    public PriorityList() {
        priorities.add("Low");
        priorities.add("Medium");
        priorities.add("High");
        priorities.add("Critical");

    }

    public Long getPriorityListId() {
        return priorityListId;
    }

    public List<String> getPriorities() {
        return priorities;
    }

    public void setPriorities(ArrayList<String> newPriorities) {
        priorities = newPriorities;
    }

    public void addPriorityToList(String priority){priorities.add(priority);}

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
