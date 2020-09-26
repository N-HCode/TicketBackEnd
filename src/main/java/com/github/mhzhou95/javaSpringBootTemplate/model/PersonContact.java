package com.github.mhzhou95.javaSpringBootTemplate.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Entity
public class PersonContact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) private Long id;

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    private OrganizationContact organizationContact;
    private String phoneNumber;
    private final LocalDateTime dateCreated = LocalDateTime.now();
    private LocalDateTime lastModified;

    public PersonContact() {
    }

    public PersonContact(@NotNull String firstName, @NotNull String lastName, @NotNull String email, OrganizationContact organizationContact, String phoneNumber, LocalDateTime lastModified) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.organizationContact = organizationContact;
        this.phoneNumber = phoneNumber;
        this.lastModified = lastModified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public OrganizationContact getOrganizationContact() {
        return organizationContact;
    }

    public void setOrganizationContact(OrganizationContact organizationContact) {
        this.organizationContact = organizationContact;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }
}
