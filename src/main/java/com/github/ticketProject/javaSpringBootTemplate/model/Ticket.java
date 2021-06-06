package com.github.ticketProject.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

// it has red underline here but the table is still made to be called tickets. Runs with no problems because using h2 db
@Entity
@Table(name = "ticket")
public class Ticket {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY) private long ticketNumber;
    @NotNull private String subject;
    @NotNull private String description;
    private String resolution;
    private String priority;
    private ZonedDateTime dateCreated;
    private ZonedDateTime dateClosed;
    private ZonedDateTime lastModified;
    private String ticketNotes;
    private String responses;
    private String status;
//    Blob example
//    @Lob
//    private byte[] blobData;

    // @JsonManagedReference and @JsonBackReference to solve infinite recursion problem
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "user")
    @JsonBackReference(value = "user-tickets")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
    @JsonIdentityReference(alwaysAsId = true)
    private User user;

    private Long userId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn( name = "organization_id")
//    @JsonBackReference(value = "organization-tickets")
//    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "contact")
    @JsonBackReference( value = "contact-tickets")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Contact contact;

    private Long contactId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "client_organization")
    @JsonBackReference(value = "client_organization-tickets")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private ClientsOrganization clientsOrganization;

    private Long clientsOrganizationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "ticket_list")
    @JsonBackReference(value = "ticket_list-ticket")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)

    private TicketList ticketList;

    private Long ticketListId;

    public Ticket() {
    }

    public Ticket(@NotNull String subject,
                  @NotNull String description,
                  String resolution,
                  String priority,
                  String status,
                  User user,
                  Contact contact,
                  ClientsOrganization clientsOrganization,
                  TicketList ticketList) {

        this.subject = subject;
        this.description = description;
        this.resolution = resolution;
        this.priority = priority;
        this.status = status;
        this.user = user;
        this.userId = user.getUserId();
        this.contact = contact;
        this.contactId = contact.getId();
        this.clientsOrganization = clientsOrganization;
        this.clientsOrganizationId = clientsOrganization.getId();
        this.ticketList = ticketList;
        this.ticketListId = ticketList.getId();
    }

    public long getTicketNumber() {
        return ticketNumber;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTicketNotes() {
        return ticketNotes;
    }

    public void setTicketNotes(String ticketNotes) {
        this.ticketNotes = ticketNotes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ZonedDateTime getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(ZonedDateTime dateClosed) {
        this.dateClosed = dateClosed;
    }

    public ZonedDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(ZonedDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public String getResponses() {
        return responses;
    }

    public void setResponses(String responses) {
        this.responses = responses;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TicketList getTicketList() {
        return ticketList;
    }

    public void setTicketList(TicketList ticketList) {
        this.ticketList = ticketList;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public ClientsOrganization getClientsOrganization() {
        return clientsOrganization;
    }

    public void setClientsOrganization(ClientsOrganization clientsOrganization) {
        this.clientsOrganization = clientsOrganization;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Long getClientsOrganizationId() {
        return clientsOrganizationId;
    }

    public void setClientsOrganizationId(Long clientsOrganizationId) {
        this.clientsOrganizationId = clientsOrganizationId;
    }

    public Long getTicketListId() {
        return ticketListId;
    }

    public void setTicketListId(Long ticketListId) {
        this.ticketListId = ticketListId;
    }
}
