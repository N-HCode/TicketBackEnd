package com.github.mhzhou95.javaSpringBootTemplate.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.AUTO) private Long id;
    @NotNull String username;
    @NotNull String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String userRole;
    private Date dateCreated;
    private Date lastLogin;
    private Date lastModified;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Ticket> tickets;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Organization> organizations;

    public User() {
    }

    public User(@NotNull String username, @NotNull String password, String firstName, String lastName, String email, String userRole, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userRole = userRole;
        this.phoneNumber = phoneNumber;
        this.tickets = new HashSet<>();
        this.organizations = new HashSet<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Long getId() {
        return id;
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void addTicket(Ticket ticket){
        this.tickets.add(ticket);
    }

    public Set<Organization> getOrganizations() {
        return organizations;
    }

   public void addOrganization(Organization organization){
        this.organizations.add(organization);
   }
}
