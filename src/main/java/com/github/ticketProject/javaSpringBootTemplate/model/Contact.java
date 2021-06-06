package com.github.ticketProject.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.*;

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
    private Long id;

    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String phoneNumber;
    private final ZonedDateTime dateCreated = ZonedDateTime.now();
    private ZonedDateTime lastModified = ZonedDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "contact_list" )
    @JsonBackReference(value = "contact_list-contact")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private ContactList contactList;

    private Long contactListId;

//    // @JsonManagedReference and @JsonBackReference to solve infinite recursion problem
//    @OneToMany(mappedBy = "contact")
//    @JsonManagedReference( value = "contact-tickets")
//    private final Set<Ticket> tickets= new HashSet<>();




    public Contact() {
    }

    public Contact(String firstName, String lastName, String fullName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }


    public Long getId() {
        return id;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
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


    public ZonedDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(ZonedDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public ContactList getContactList() {
        return contactList;
    }

    public void setContactList(ContactList contactList) {
        this.contactList = contactList;
        this.contactListId = contactList.getId();
    }

    public Long getContactListId() {
        return contactListId;
    }
}
