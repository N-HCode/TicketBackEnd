package com.github.mhzhou95.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prioritylists")
public class PriorityList {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long priorityListId;

    //@ElementCollection annotation is used to store a list of values as an entity attribute without needing to model an additional entity
    //@OneToMany as OneToMany is for one-Entity-many-Entity relationship
    @ElementCollection
    @JsonManagedReference
    private List<String> Priorities = new ArrayList<>();

    public PriorityList() {
    }

    public Long getPriorityListId() {
        return priorityListId;
    }

    public List<String> getPriorities() {
        return Priorities;
    }

    public void setPriorities(ArrayList<String> priorities) {
        Priorities = priorities;
    }
}
