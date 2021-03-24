package com.github.ticketProject.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "client_organization_list")
public class ClientsOrganizationList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="client_organization_list_id")
    private Long Id;

    @OneToMany(mappedBy = "clientsOrganizationList", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "client_organization_list-client_organization")
    private final Set<ClientsOrganization> clientsOrganizations = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference(value = "organization-client_organization_list")
    private Organization organization;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "client_organization_list-ticket_list")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "Id")
    @JsonIdentityReference(alwaysAsId = true)
    private TicketList ticketList;

    public ClientsOrganizationList() {
    }

    public Long getId() {
        return Id;
    }

    public Set<ClientsOrganization> getClientsOrganizations() {
        return clientsOrganizations;
    }

    public void addClientsOrganization(ClientsOrganization clientsOrganization){
        clientsOrganizations.add(clientsOrganization);
    }
}
