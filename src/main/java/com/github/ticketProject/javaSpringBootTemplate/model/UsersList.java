package com.github.ticketProject.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users_list")
public class UsersList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_list_id")
    private Long userListId;

    @OneToMany(mappedBy = "usersList", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference(value="users-list-user")
//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
//    @JsonIdentityReference(alwaysAsId = true)
    private final Set<User> users = new HashSet<>();

    @OneToOne
    @JsonBackReference(value = "organization-user_list")
    private Organization organization;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "user_list-ticket_list")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private TicketList ticketList;

    public UsersList() {
    }

    public UsersList(Organization organization) {
        this.organization = organization;
    }

    public void addUser(User user){
        users.add(user);
    }

    public Set<User> getUsers() {
        return users;
    }

    public Long getUserListId() {
        return userListId;
    }

    public Organization getOrganization() {
        return organization;
    }

    public TicketList getTicketList() {
        return ticketList;
    }

    public void setTicketList(TicketList ticketList) {
        this.ticketList = ticketList;
    }
}
