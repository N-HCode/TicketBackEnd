package com.github.ticketProject.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ticket_list")
public class TicketList {

    @Id
    @Column(name ="ticket_list_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "ticketList", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "ticket_list-ticket")
    private final Set<Ticket> tickets = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "organization-ticket_list")
//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "Id")
//    @JsonIdentityReference(alwaysAsId = true)
    private Organization organization;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "client_organization_list-ticket_list")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private ClientsOrganizationList clientsOrganizationLists;

    @OneToMany
    @JsonManagedReference(value = "contact_list-ticket_list")
//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "Id")
//    @JsonIdentityReference(alwaysAsId = true)
    private final Set<ContactList> contactLists = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "user_list-ticket_list")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userListId")
    @JsonIdentityReference(alwaysAsId = true)
    private UsersList usersList;

    public TicketList() {
    }

    public TicketList(Organization organization, ClientsOrganizationList clientsOrganizationLists, UsersList usersList) {
        this.organization = organization;
        this.clientsOrganizationLists = clientsOrganizationLists;
        this.usersList = usersList;

        clientsOrganizationLists.setTicketList(this);
        usersList.setTicketList(this);
    }

    public long getId() {
        return id;
    }


    public Set<Ticket> getTickets() {
        return tickets;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }


}
