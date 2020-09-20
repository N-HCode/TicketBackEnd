package com.github.mhzhou95.javaSpringBootTemplate.model;

import org.springframework.beans.factory.annotation.Required;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Ticket {
    @Id @GeneratedValue (strategy = GenerationType.AUTO) private long ticketNumber;
    @NotNull private String subject;
    @NotNull private String description;
    private String userContact;
    private String userPhoneNumber;
    @NotNull private String userEmail;
    private String organizationNumber;
    private String ticketOwner;
    private String priority;
    private Date dateOpened;
    private Date dateClosed;
    private String ticketNotes;
    private String emailHistory;
    private String history;

    public Ticket() {
    }

    public Ticket(String subject, String description, String userContact, String userPhoneNumber, String userEmail, String organizationNumber, String ticketOwner, String priority){
        this.subject = subject;
        this.description = description;
        this.userContact = userContact;
        this. userPhoneNumber = userPhoneNumber;
        this.userEmail = userEmail;
        this.organizationNumber = organizationNumber;
        this.ticketOwner = ticketOwner;
        this.priority = priority;
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

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getOrganizationNumber() {
        return organizationNumber;
    }

    public void setOrganizationNumber(String organizationNumber) {
        this.organizationNumber = organizationNumber;
    }

    public String getTicketOwner() {
        return ticketOwner;
    }

    public void setTicketOwner(String ticketOwner) {
        this.ticketOwner = ticketOwner;
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

    public String getEmailHistory() {
        return emailHistory;
    }

    public void setEmailHistory(String emailHistory) {
        this.emailHistory = emailHistory;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }
}
