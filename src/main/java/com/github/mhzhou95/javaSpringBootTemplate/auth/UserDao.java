package com.github.mhzhou95.javaSpringBootTemplate.auth;

import com.github.mhzhou95.javaSpringBootTemplate.model.User;

import java.util.Optional;

//This will be the interface that will allow us to load data from
//any data source
public interface UserDao {

    User selectApplicationUserByUsername(String username);

}
