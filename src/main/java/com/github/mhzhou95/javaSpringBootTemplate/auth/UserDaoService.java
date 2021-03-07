package com.github.mhzhou95.javaSpringBootTemplate.auth;

import com.github.mhzhou95.javaSpringBootTemplate.model.User;
import com.github.mhzhou95.javaSpringBootTemplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
