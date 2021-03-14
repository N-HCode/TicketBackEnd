package com.github.mhzhou95.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String phoneNumber;
    private ZonedDateTime dateCreated;
    private ZonedDateTime lastLogin;
    private ZonedDateTime lastModified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "organization_foreign_key" )
    @JsonManagedReference
    private ClientsOrganization clientsOrganization;

    // @JsonManagedReference and @JsonBackReference to solve infinite recursion problem
    @OneToMany(mappedBy = "contact")
    @JsonBackReference
    private Set<Ticket> tickets= new HashSet<>();

    public Contact() {
    }
}
