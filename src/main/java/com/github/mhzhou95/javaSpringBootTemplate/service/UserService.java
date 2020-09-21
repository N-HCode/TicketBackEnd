package com.github.mhzhou95.javaSpringBootTemplate.service;

import com.github.mhzhou95.javaSpringBootTemplate.model.User;
import com.github.mhzhou95.javaSpringBootTemplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }


    public Optional<User> findById(Long id) {
        // Use Spring's Crud Repository Method to findById to get back an optional
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    public User createUser(User user) {
        // check if the username is already taken
        User userToCheck = userRepository.findByUsernameEquals(user.getUsername());

        // if we got back no User from the check save this new User
        if(userToCheck == null) {
            user.setDateCreated(Calendar.getInstance().getTime());
            userRepository.save(user);
            return user;
        }
        // return the user saved for the HTTP response
        return null;
    }

    public User delete(Long id) {
        // Use Spring's Crud Repository method to findById to get back an optional
        Optional<User> user = userRepository.findById(id);
        // Call the Crud Repository to deleteById if the Optional is Present
        if(user.isPresent()) {
            userRepository.deleteById(id);
        }
        // return the user that has been deleted or return null if not found
        return user.orElse(null);
    }

    public User editUser(Long id, User user) {
        // first use findById to look for user
        Optional<User> optionalUser = this.findById(id);

        // Declare a new empty User to be used to hold the user returned from calling this.findById
        User checkForUser;

        // Check the optional to see if anything is present then get the user object out else break out of this method
        if(optionalUser.isPresent() ) {
            checkForUser =  optionalUser.get();
        }
        else{
            return null;
        }

        // check if there the fields from the JSON the user sent in are not empty
        if(user.getFirstName() != null){
                checkForUser.setFirstName(user.getFirstName());
            }
            if(user.getLastName() != null){
                checkForUser.setLastName(user.getLastName());
            }
            return userRepository.save(checkForUser);
    }
}
