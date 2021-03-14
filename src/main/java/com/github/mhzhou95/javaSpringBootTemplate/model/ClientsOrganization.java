package com.github.mhzhou95.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients_organization")
public class ClientsOrganization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private boolean isForeignAddress;
    private String city;
    private String state;
    private String streetAddress;
    private String zipcode;
    private String country;
    private String organizationPhoneNumber;
    //the java.time is the newest java date API
    private final LocalDateTime dateCreated = LocalDateTime.now();
    private LocalDateTime dateModified = LocalDateTime.now();;

    @Column(name="organization_name", nullable = false,
            columnDefinition = "TEXT")
    private String organizationName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    Organization organization;

    @OneToMany(mappedBy = "clients_organization", cascade = CascadeType.ALL)
    @JsonBackReference
    Set<Contact> contacts = new HashSet<>();

    // @JsonManagedReference and @JsonBackReference to solve infinite recursion problem
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Ticket> tickets= new HashSet<>();


    public ClientsOrganization() {
    }


}
