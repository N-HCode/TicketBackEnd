package com.github.ticketProject.javaSpringBootTemplate.auth;

import com.github.ticketProject.javaSpringBootTemplate.model.User;

//This will be the interface that will allow us to load data from
//any data source
public interface UserDao {

    User selectApplicationUserByUsername(String username);

}
