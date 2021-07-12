package com.github.ticketProject.javaSpringBootTemplate.service;

import com.github.ticketProject.javaSpringBootTemplate.model.Organization;
import com.github.ticketProject.javaSpringBootTemplate.model.Role;
import com.github.ticketProject.javaSpringBootTemplate.model.User;
import com.github.ticketProject.javaSpringBootTemplate.model.UsersList;
import com.github.ticketProject.javaSpringBootTemplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

import static com.github.ticketProject.javaSpringBootTemplate.authorization.Roles.ADMIN;
import static com.github.ticketProject.javaSpringBootTemplate.authorization.Roles.USER;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;



    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;

        // create default user for testing
//        User defaultUser = new User("admin", "password", "firstname", "lastname", "admin@gmail.com", "admin", "666-666-6666");
//        createUser(defaultUser);

        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }


    public Optional<User> findById(Long idOfUser) {
        // Use Spring's Crud Repository Method to findById to get back an optional
        return userRepository.findById(idOfUser);
    }

    public Iterable<User> findByOrg(Organization organization) {
        return userRepository.findAllByUsersListEquals(organization.getUsersList());
    }

    public boolean createUser(UsersList usersList , User user) {
        // check if the username is already taken
        User userToCheck = userRepository.findByUsernameEquals(user.getUsername());

        // if we got back no User from the check save this new User
        if(userToCheck == null) {
            ZonedDateTime timeAsOfNow = ZonedDateTime.now();
            user.setUsersList(usersList);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setDateCreated(timeAsOfNow);
            user.setLastModified(timeAsOfNow);
            switch (user.getUserRole().toLowerCase()){
                case "user":
                    user.addRole(USER.getRoleInEnum());
                    break;
                case "admin":
                    user.addRole(ADMIN.getRoleInEnum());
                    break;
                default:
                    break;
            }

            userRepository.save(user);
            return true;
        }
        // return the user saved for the HTTP response
        return false;
    }

    public boolean delete(UsersList usersList, Long idOfUser) {
        // Use Spring's Crud Repository method to findById to get back an optional
        Optional<User> user = userRepository.findById(idOfUser);
        // Call the Crud Repository to deleteById if the Optional is Present
        if(user.isPresent() && user.get().getUsersList().equals(usersList)) {
            userRepository.deleteById(idOfUser);
            return true;
        }
        // return the user that has been deleted or return null if not found
        return false;
    }

    public User editUser(UsersList usersList, Long idOfUser, User user) {
        // first use findById to look for user
        Optional<User> findUser = this.findById(idOfUser);

        // Check the optional to see if anything is present then get the user object out else break out of this method
        if(findUser.isPresent() && findUser.get().getUsersList().equals(usersList)) {
            User returnedUser = findUser.get();
            // Set the changeable fields to the new fields if any change
            returnedUser.setFirstName(user.getFirstName());
            returnedUser.setLastName(user.getLastName());
            returnedUser.setEmail(user.getEmail());
            returnedUser.setPassword(user.getPassword());
            returnedUser.setPhoneNumber(user.getPhoneNumber());
            returnedUser.setLastModified(ZonedDateTime.now());
            returnedUser.setUserRole(user.getUserRole());
            switch (user.getUserRole().toLowerCase()){
                case "user":
                    user.addRole(USER.getRoleInEnum());
                    break;
                case "admin":
                    user.addRole(ADMIN.getRoleInEnum());
                    break;
                default:
                    break;
            }

            return userRepository.save(returnedUser);

        }
        else{
            return null;
        }
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

    public User getUserByUsername(String username){

        try{
            return userRepository.findByUsernameEquals(username);
        }
        catch (Exception e){
            return null;
        }

    }

    public boolean addRoleTouser(Authentication authResult, String roleName){

        User user = getUserByUsername(authResult.getName());
        if (user == null){
            return false;
        }

        Role role = roleService.findRoleByName(roleName);
        if (role == null){
            return false;
        }


        user.addRole(role);
        //You do not need to save as JPA will persist any changes made to a returned entity.
//        userRepository.save()

        return true;
    }




}
