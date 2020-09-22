package com.github.mhzhou95.javaSpringBootTemplate.service;

import com.github.mhzhou95.javaSpringBootTemplate.model.Organization;
import com.github.mhzhou95.javaSpringBootTemplate.model.Ticket;
import com.github.mhzhou95.javaSpringBootTemplate.model.User;
import com.github.mhzhou95.javaSpringBootTemplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Calendar;
import java.util.Date;
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

    public Optional<User> findById(Long idOfUser) {
        // Use Spring's Crud Repository Method to findById to get back an optional
        Optional<User> user = userRepository.findById(idOfUser);
        return user;
    }

    public User createUser(User user) {
        // check if the username is already taken
        User userToCheck = userRepository.findByUsernameEquals(user.getUsername());

        // if we got back no User from the check save this new User
        if(userToCheck == null) {
            Date timeAsOfNow = Calendar.getInstance().getTime();
            user.setDateCreated(timeAsOfNow);
            user.setLastModified(timeAsOfNow);
            userRepository.save(user);
            return user;
        }
        // return the user saved for the HTTP response
        return null;
    }

    public User delete(Long idOfUser) {
        // Use Spring's Crud Repository method to findById to get back an optional
        Optional<User> user = userRepository.findById(idOfUser);
        // Call the Crud Repository to deleteById if the Optional is Present
        if(user.isPresent()) {
            userRepository.deleteById(idOfUser);
        }
        // return the user that has been deleted or return null if not found
        return user.orElse(null);
    }

    public User editUser(Long idOfUser, User user) {
        // first use findById to look for user
        Optional<User> findUser = this.findById(idOfUser);

        // Check the optional to see if anything is present then get the user object out else break out of this method
        if(findUser.isPresent() ) {
            User returnedUser = findUser.get();
            // Set the changeable fields to the new fields if any change
            returnedUser.setFirstName(user.getFirstName());
            returnedUser.setLastName(user.getLastName());
            returnedUser.setEmail(user.getEmail());
            returnedUser.setPassword(user.getPassword());
            returnedUser.setPhoneNumber(user.getPhoneNumber());
            returnedUser.setLastModified(Calendar.getInstance().getTime());
            return userRepository.save(returnedUser);
        }
        else{
            return null;
        }
    }

    public User loginUser(String username, String password) {
        // Use Spring's Crud Repository method to find a user that equals the params
        User user = userRepository.findByUsernameEqualsAndPasswordEquals(username, password);
        return user;
    }

    public Ticket addTicketToUser(Long idOfUser, Ticket ticket) {
        // find the user first
        Optional<User> user = findById(idOfUser);

        // check if the user was found
        if( user.isPresent()) {
            // add the ticket to the User's Set
            user.get().addTicket(ticket);
            return ticket;
        }
        return null;
    }

    public Organization addOrganizationToUser(Long idOfUser, Organization organization) {
        // find the user first
        Optional<User> user = findById(idOfUser);

        // check if the user was found
        if(user.isPresent()) {
            // set the organization as the user
            user.get().addOrganization(organization);
            return organization;
        }
        return null;
    }
}
