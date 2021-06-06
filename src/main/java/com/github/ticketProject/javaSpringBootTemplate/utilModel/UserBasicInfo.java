package com.github.ticketProject.javaSpringBootTemplate.utilModel;

import com.github.ticketProject.javaSpringBootTemplate.model.Role;
import com.github.ticketProject.javaSpringBootTemplate.model.User;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class UserBasicInfo {

    @NotNull
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String email;
    private String phoneNumber;

    private Set<Role> userRoles;

    public UserBasicInfo(User user) {
        this.id = user.getUserId();
        this.firstName = user.getFirstName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.userRoles = user.getUserRoles();
    }

    //[org.springframework.http.converter.HttpMessageNotWritableException: No converter found for return value of type:
    //Need getters oor you will get this, if you try to send this object through a response
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }


    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Set<Role> getUserRoles() {
        return userRoles;
    }
}
