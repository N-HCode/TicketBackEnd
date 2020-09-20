package com.github.mhzhou95.javaSpringBootTemplate.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import
import com.github.mhzhou95.javaSpringBootTemplate.UserRole;

@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.AUTO) private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String userRole;
    private Date dateCreated;
    private Date lastLogin;
    private Date lastModified;

    public User() {
    }

    public User(String firstName, String lastName, String email, Date dateCreated, Date lastLogin, Date lastModified) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateCreated = dateCreated;
        this.lastLogin = lastLogin;
        this.lastModified = lastModified;
        this.userRole = UserRole.STANDARD.toString();
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
}
