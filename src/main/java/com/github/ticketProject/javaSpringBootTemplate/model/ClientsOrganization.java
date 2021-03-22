package com.github.ticketProject.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
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
    private final ZonedDateTime dateCreated = ZonedDateTime.now();
    private final ZonedDateTime dateModified = ZonedDateTime.now();;

    @Column(name="client_organization_name", nullable = false,
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
    @JsonBackReference(value="organization-client_organization")
    private Organization organization;

    //When using One to Many we need the mapped by.
    //The Mapped by use the property name of the model related to the field
    //For example, for this Mapped by, it is related to the Contact model/class
    //So we go into the contact class and look for the property we want to map by
    //Usually this will be the Many to One in the Contact class
    //So we open the Contact model and then look for the field and then we make sure
    //We sure the exact same name. In this example that is "clientsOrganization"
    @OneToMany(mappedBy = "clientsOrganization", cascade = CascadeType.ALL)
    //https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
    //https://stackoverflow.com/questions/33475222/spring-boot-jpa-json-without-nested-object-with-onetomany-relation
    @JsonManagedReference(value = "client_organization-contacts")
    private final Set<Contact> contacts = new HashSet<>();

    // @JsonManagedReference and @JsonBackReference to solve infinite recursion problem
    @OneToMany(mappedBy = "clientsOrganization", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "client_organization-tickets")
    private final Set<Ticket> tickets= new HashSet<>();


    public ClientsOrganization() {
    }

    public ClientsOrganization(boolean isForeignAddress, String city, String state, String streetAddress, String zipcode, String country, String organizationPhoneNumber, String organizationName, Organization organization) {
        this.isForeignAddress = isForeignAddress;
        this.city = city;
        this.state = state;
        this.streetAddress = streetAddress;
        this.zipcode = zipcode;
        this.country = country;
        this.organizationPhoneNumber = organizationPhoneNumber;
        this.organizationName = organizationName;
        this.organization = organization;
    }

    public void addContact(Contact contact){contacts.add(contact);}

    public void addTicket(Ticket ticket){ tickets.add(ticket);}


}
