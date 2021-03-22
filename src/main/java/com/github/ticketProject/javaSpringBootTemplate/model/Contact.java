package com.github.ticketProject.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="contact_id")
    private Long Id;

    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String phoneNumber;
    private final ZonedDateTime dateCreated = ZonedDateTime.now();
    private ZonedDateTime lastLogin;
    private ZonedDateTime lastModified = ZonedDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "contact_list_id" )
    @JsonBackReference(value = "contact_list-contact")
    private ContactList contactList;

    // @JsonManagedReference and @JsonBackReference to solve infinite recursion problem
    @OneToMany(mappedBy = "contact")
    @JsonManagedReference( value = "contact-tickets")
    private final Set<Ticket> tickets= new HashSet<>();



    public Contact() {
    }

    public Contact(String firstName, String lastName, String fullName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void addTicket(Ticket ticket){tickets.add(ticket);}

    public Long getId() {
        return Id;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ZonedDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(ZonedDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public ZonedDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(ZonedDateTime lastModified) {
        this.lastModified = lastModified;
    }

}
