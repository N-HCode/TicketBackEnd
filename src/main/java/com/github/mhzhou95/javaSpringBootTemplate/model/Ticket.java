package com.github.mhzhou95.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

// it has red underline here but the table is still made to be called tickets. Runs with no problems because using h2 db
@Entity
@Table(name = "tickets")
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
    private String assignedTo;
//    Blob example
//    @Lob
//    private byte[] blobData;

    // @JsonManagedReference and @JsonBackReference to solve infinite recursion problem
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "user_foreign_key")
    @JsonBackReference
    private User user;

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

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
}
