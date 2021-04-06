package com.github.ticketProject.javaSpringBootTemplate.service;

import com.github.ticketProject.javaSpringBootTemplate.model.Organization;
import com.github.ticketProject.javaSpringBootTemplate.model.User;
import com.github.ticketProject.javaSpringBootTemplate.model.UsersList;
import com.github.ticketProject.javaSpringBootTemplate.repository.OrganizationRepository;
import com.github.ticketProject.javaSpringBootTemplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import static com.github.ticketProject.javaSpringBootTemplate.authorization.Roles.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



import java.time.ZonedDateTime;
import java.util.*;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository, UserRepository userRepository, UserService userService, PasswordEncoder passwordEncoder) {
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    public Organization findById(Long id) {
        //Optional are used to avoid null exception.
        //It is just a container object which may or may not contain a non-null value
        return organizationRepository.findById(id).orElse(null);

    }

    public Organization createOrganization(String username, String password, Organization organization)
    {

        
//        Boolean isAdmin = user.map( user1 -> user1.getUserRole().equals("admin")).orElse(false);

        //make sure the body is not null
        if (organization != null){

            User userToCheck = userRepository.findByUsernameEquals(username);

            if (userToCheck != null) {
                return null;
            }

            Organization newlyCreatedOrganization = new Organization(organization.getOrganizationName(), organization.isForeignAddress()
                    ,organization.getCity(),organization.getState(),organization.getStreetAddress(),organization.getZipcode()
                    ,organization.getCountry(),organization.getOrganizationPhoneNumber());
            newlyCreatedOrganization.setAccountNumber(Organization.getAccSeq());

            User rootUser = new User();
            rootUser.setUsername(username);
            rootUser.setPassword(passwordEncoder.encode(password));
            rootUser.setUserRole("root");
            rootUser.setFirstName("root");
            rootUser.setLastName("user");
            rootUser.addRole(ROOT.getRoleInEnum());
            rootUser.setEmail("Test");
            ZonedDateTime timeAsOfNow = ZonedDateTime.now();

            rootUser.setDateCreated(timeAsOfNow);
            rootUser.setLastModified(timeAsOfNow);
            rootUser.setFullName(rootUser.getFirstName() + " " + rootUser.getLastName());



            newlyCreatedOrganization.getUsersList().addUser(rootUser);
            organizationRepository.save(newlyCreatedOrganization);
            rootUser.setUsersList(newlyCreatedOrganization.getUsersList());
            userRepository.save(rootUser);
//            User createUser = userService.createUser(newlyCreatedOrganization,rootUser);

            return newlyCreatedOrganization;
        } else {
            return null;
        }
    }

    public Organization delete(Long id) {
        Organization organization = this.findById(id);

        if (organization != null){
            organizationRepository.deleteById(id);
        }

        return organization;

    }


    public Organization editOrganization(Long id, Organization newOrgInfo) {

        Organization editableOrg = this.findById(id);

        editableOrg.setForeignAddress(newOrgInfo.isForeignAddress());
        editableOrg.setOrganizationName(newOrgInfo.getOrganizationName());
        editableOrg.setDateModified(ZonedDateTime.now());
        if(newOrgInfo.isForeignAddress()){
            editableOrg.setCity(newOrgInfo.getCity());
            editableOrg.setState("");
            editableOrg.setStreetAddress(newOrgInfo.getStreetAddress());
            editableOrg.setCountry(newOrgInfo.getCountry());
            editableOrg.setZipcode("");
        }else{
            editableOrg.setCity(newOrgInfo.getCity());
            editableOrg.setState(newOrgInfo.getState());
            editableOrg.setStreetAddress(newOrgInfo.getStreetAddress());
            editableOrg.setCountry(newOrgInfo.getCountry());
            editableOrg.setZipcode(newOrgInfo.getZipcode());
        }

        organizationRepository.save(editableOrg);
        return editableOrg;
    }

    public Set<User> addUserToOrgContacts(Organization org, User user){
        Set<User> orgContacts = org.getUsersList().getUsers();
        orgContacts.add(user);
        return orgContacts;
    }


    public Organization findByUserId(Long id) {
        User user = userRepository.findById(id).orElse(null);

        return organizationRepository.findByUsersListContains(user.getUsersList()).orElse(null);
    }

//    public void addStatusListIdToOrg(Organization organization,Long statusListId){
//        organization.setStatusListId(statusListId);
//        organizationRepository.save(organization);
//    }
//
//    public void addPriorityListIdToOrg(Organization organization,Long priorityListId){
//        organization.setPriorityListId(priorityListId);
//        organizationRepository.save(organization);
//    }

    public Page<User> getUsersFromOrganization(UsersList usersList, int pageNo, int numberPerPage){

        Pageable pageConfig = PageRequest.of(pageNo, numberPerPage);

        return userRepository.findAllByUsersListEquals(usersList, pageConfig);
    }

}
