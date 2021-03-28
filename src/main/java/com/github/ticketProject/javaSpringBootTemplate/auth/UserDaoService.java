package com.github.ticketProject.javaSpringBootTemplate.auth;

import com.github.ticketProject.javaSpringBootTemplate.model.User;
import com.github.ticketProject.javaSpringBootTemplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("UserDaoService")
public class UserDaoService implements UserDao{


    private UserRepository userRepository;

    @Autowired
    public UserDaoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User selectApplicationUserByUsername(String username) {


        return userRepository.findByUsernameEquals(username);
    }
}
