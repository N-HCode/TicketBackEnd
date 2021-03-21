package com.github.ticketProject.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    @JoinColumn( name = "user_id")
    @JsonManagedReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "organization_id")
    @JsonManagedReference
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "contact_id")
    @JsonManagedReference
    private Contact contact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "client_organization_id")
    @JsonManagedReference
    private ClientsOrganization clientsOrganization;

    public Ticket() {
    }

    public Ticket(String subject, String description, String priority){
        this.subject = subject;
        this.description = description;
        this.priority = priority;
        this.status = "new";
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

}
