package com.github.mhzhou95.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "status_list")
public class StatusList {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long statusListId;

    @OneToOne
    @JsonManagedReference
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



    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(ArrayList<String> statusList) {
        this.statusList = statusList;
    }

    public Long getStatusListId() {
        return statusListId;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
