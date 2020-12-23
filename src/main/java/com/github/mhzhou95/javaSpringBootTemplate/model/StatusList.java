package com.github.mhzhou95.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "statuslists")
public class StatusList {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long statusListId;

    //@ElementCollection annotation is used to store a list of values as an entity attribute without needing to model an additional entity
    //@OneToMany as OneToMany is for one-Entity-many-Entity relationship
    @ElementCollection
    @JsonManagedReference
    private Set<String> statusList = new HashSet<>();

    public StatusList() {
    }

    public Set<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(Set<String> statusList) {
        this.statusList = statusList;
    }

    public Long getStatusListId() {
        return statusListId;
    }
}
