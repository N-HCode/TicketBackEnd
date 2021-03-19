package com.github.mhzhou95.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "clients_organization")
@Table(name = "clients_organization")
public class ClientsOrganization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="client_organization_id")
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

    @Column(name="clientsorganization", nullable = false,
            columnDefinition = "TEXT")
    private String organizationName;

    //When using the Many to one we need a Join Column
    //Many to one will be used with a Join Column.
    //The name will be the primary key of the model we are relating to
    //This is essentially created a primary/foreign key relationship
    //We can use the column name. Column name can be specified using the @Column(name="")
    //For this example there is a column named organization_id in the Organization model/class
    //We use that to be the foreign key and Join the Columns
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="organization_id")
    @JsonManagedReference
    private Organization organization;

    //When using One to Many we need the mapped by.
    //The Mapped by use the property name of the model related to the field
    //For example, for this Mapped by, it is related to the Contact model/class
    //So we go into the contact class and look for the property we want to map by
    //Usually this will be the Many to One in the Contact class
    //So we open the Contact model and then look for the field and then we make sure
    //We sure the exact same name. In this example that is "clientsOrganization"
    @OneToMany(mappedBy = "clientsOrganization", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Contact> contacts = new HashSet<>();

    // @JsonManagedReference and @JsonBackReference to solve infinite recursion problem
    @OneToMany(mappedBy = "clientsOrganization", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Ticket> tickets= new HashSet<>();


    public ClientsOrganization() {
    }


}
