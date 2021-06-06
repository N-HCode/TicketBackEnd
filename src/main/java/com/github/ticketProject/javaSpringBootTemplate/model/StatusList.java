package com.github.ticketProject.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "status_list")
public class StatusList {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long statusListId;

    @OneToOne
    @JsonBackReference(value="organization-status_list")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Organization organization;

    //@ElementCollection annotation is used to store a list of values as an entity attribute without needing to model an additional entity
    //@OneToMany as OneToMany is for one-Entity-many-Entity relationship
    @ElementCollection
    private List<String> statusList = new ArrayList<>();

    public StatusList() {
        statusList.add("New");
        statusList.add("In Progress");
        statusList.add("Closed");
    }

    public StatusList(Organization organization) {
        this();
        this.organization = organization;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public Long getStatusListId() {
        return statusListId;
    }

    public void addStatus(String status){statusList.add(status);}

    public void removeStatus(String status){statusList.remove(status);}

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }


}
