package com.github.mhzhou95.javaSpringBootTemplate.service;

import com.github.mhzhou95.javaSpringBootTemplate.model.Organization;
import com.github.mhzhou95.javaSpringBootTemplate.model.User;
import com.github.mhzhou95.javaSpringBootTemplate.repository.OrganizationRepository;
import com.github.mhzhou95.javaSpringBootTemplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrganizationService {

    private OrganizationRepository organizationRepository;
    private UserRepository userRepository;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository, UserRepository userRepository, UserService userService, PasswordEncoder passwordEncoder) {
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public Iterable<Organization> findAll()
    {
        return organizationRepository.findAll();
    }

    public Organization findById(Long id) {
        //Optional are used to avoid null exception.
        //It is just a container object which may or may not contain a non-null value
        return organizationRepository.findById(id).orElse(null);

    }

    public Organization createOrganization(String username, String password, Organization organization)
    {   User rootUser = new User();
        rootUser.setUsername(username);
        rootUser.setPassword(passwordEncoder.encode(password));
        rootUser.setUserRole("root");
        rootUser.setFirstName("root");
        rootUser.setLastName("user");
        User createUser = userService.createUser(rootUser);

//        Boolean isAdmin = user.map( user1 -> user1.getUserRole().equals("admin")).orElse(false);

        //make sure the body is not null
        if (organization != null){
            organization.setAccountNumber(Organization.getAccSeq());
            createUser.setOrganization(organization);
            return organizationRepository.save(organization);
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
        editableOrg.setDateModified(LocalDateTime.now());
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
        Set<User> orgContacts = org.getUsers();
        orgContacts.add(user);
        return orgContacts;
    }


    public Organization findByUserId(Long id) {
        User user = userRepository.findById(id).orElse(null);

        return organizationRepository.findByUsersContains(user).orElse(null);
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
}
