package com.github.ticketProject.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "contact_list")
public class ContactList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="contact_list_id")
    private Long Id;

    @OneToMany(mappedBy = "contactList", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "contact_list-contact")
    private final Set<Contact> contacts = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference(value = "client_organization-contact_list")
    private ClientsOrganization clientsOrganization;

    public ContactList() {
    }

    public Long getId() {
        return Id;
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
}
