package com.github.ticketProject.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "contact_list")
public class ContactList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="contact_list_id")
    private Long id;

    @OneToMany(mappedBy = "contactList", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "contact_list-contact")
    private final Set<Contact> contacts = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference(value = "client_organization-contact_list")
    private ClientsOrganization clientsOrganization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "contact_list-ticket_list")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private TicketList ticketList;


    public ContactList() {
    }

    public ContactList(ClientsOrganization clientsOrganization) {
        this.clientsOrganization = clientsOrganization;
    }

    public Long getId() {
        return id;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void addContact(Contact contact){
        contacts.add(contact);
    }

    public ClientsOrganization getClientsOrganization() {
        return clientsOrganization;
    }

    public void setClientsOrganization(ClientsOrganization clientsOrganization) {
        this.clientsOrganization = clientsOrganization;
    }

    public TicketList getTicketList() {
        return ticketList;
    }

    public void setTicketList(TicketList ticketList) {
        this.ticketList = ticketList;
    }
}
