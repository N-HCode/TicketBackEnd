package com.github.ticketProject.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users_list")
public class UsersList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_list_id")
    private Long Id;

    @OneToMany(mappedBy = "usersList", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference(value="users-list-user")
//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
//    @JsonIdentityReference(alwaysAsId = true)
    private final Set<User> users = new HashSet<>();

    public UsersList() {
    }

    public void addUser(User user){
        users.add(user);
    }

    public Set<User> getUsers() {
        return users;
    }
}
