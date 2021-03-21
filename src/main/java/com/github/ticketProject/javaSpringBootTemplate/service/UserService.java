package com.github.ticketProject.javaSpringBootTemplate.service;

import com.github.ticketProject.javaSpringBootTemplate.model.Organization;
import com.github.ticketProject.javaSpringBootTemplate.model.User;
import com.github.ticketProject.javaSpringBootTemplate.repository.OrganizationRepository;
import com.github.ticketProject.javaSpringBootTemplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private OrganizationRepository organizationRepository;


    @Autowired
    public UserService(UserRepository userRepository, OrganizationRepository organizationRepository) {
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
        // create default user for testing
//        User defaultUser = new User("admin", "password", "firstname", "lastname", "admin@gmail.com", "admin", "666-666-6666");
//        createUser(defaultUser);

    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long idOfUser) {
        // Use Spring's Crud Repository Method to findById to get back an optional
        return userRepository.findById(idOfUser);
    }

    public Iterable<User> findByOrg(Organization organization) {
        return userRepository.findAllByOrganizationEquals(organization);
    }

    public User createUser(User user) {
        // check if the username is already taken
        User userToCheck = userRepository.findByUsernameEquals(user.getUsername());

        // if we got back no User from the check save this new User
        if(userToCheck == null) {
            ZonedDateTime timeAsOfNow = ZonedDateTime.now();
            user.setDateCreated(timeAsOfNow);
            user.setLastModified(timeAsOfNow);
            user.setFullName(user.getFirstName() + " " + user.getLastName());
            userRepository.save(user);
            return user;
        }
        // return the user saved for the HTTP response
        return null;
    }

    public boolean delete(Long idOfUser) {
        // Use Spring's Crud Repository method to findById to get back an optional
        Optional<User> user = userRepository.findById(idOfUser);
        // Call the Crud Repository to deleteById if the Optional is Present
        if(user.isPresent()) {
            userRepository.deleteById(idOfUser);
            return true;
        }
        // return the user that has been deleted or return null if not found
        return false;
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
            returnedUser.setLastModified(ZonedDateTime.now());
            returnedUser.setUserRole(user.getUserRole());
            return userRepository.save(returnedUser);

        }
        else{
            return null;
        }
    }


    public Organization addOrganizationToUser(Long idOfUser, Long idOfOrganization) {
        // find the user first
        Optional<User> user = findById(idOfUser);
        Optional<Organization> organization = organizationRepository.findById(idOfOrganization);
        // check if the user was found
        if(user.isPresent() && organization.isPresent()) {
            Organization organizationExist = organization.get();
            User userExist = user.get();
            userExist.setOrganization(organizationExist);
            userRepository.save(userExist);
            // set the organization as the user
            return organization.get();
        }
        return null;
    }

    public boolean checkUsernameIsTaken(String username) {
        // check if the username is already taken
        User userToCheck = userRepository.findByUsernameEquals(username);

        // if this username is not taken
        if(userToCheck == null) {
            return false;
        }
        // return the user saved for the HTTP response
        return true;
    }



}
