package com.github.mhzhou95.javaSpringBootTemplate.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

public class Organization {
    @Id @GeneratedValue(strategy = GenerationType.AUTO) private Long id;

    private String organizationName;
    private long accountNumber;
    private List<User> contacts = new ArrayList<>();
    //private List<Ticket> allUserCases = new ArrayList<>();
    private String state;
    private String streetAddress;
    private String zipcode;
    private String Country;
    private String organizationPhoneNumber;

    public Organization(Long id, String organizationName, long accountNumber/*,List<User> contacts, List<Ticket> allUserCases */, String state, String streetAddress, String zipcode, String country, String organizationPhoneNumber) {
        this.id = id;
        this.organizationName = organizationName;
        this.accountNumber = accountNumber;
        this.contacts = contacts;
//        this.allUserCases = allUserCases;
        this.state = state;
        this.streetAddress = streetAddress;
        this.zipcode = zipcode;
        Country = country;
        this.organizationPhoneNumber = organizationPhoneNumber;
    }

    public Long getId() {
        return id;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public List<User> getContacts() {
        return contacts;
    }

    public void setContacts(List<User> contacts) {
        this.contacts = contacts;
    }

//    public List<Ticket> getAllUserCases() {
//        return allUserCases;
//    }
//
//    public void setAllUserCases(List<Ticket> allUserCases) {
//        this.allUserCases = allUserCases;
//    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getOrganizationPhoneNumber() {
        return organizationPhoneNumber;
    }

    public void setOrganizationPhoneNumber(String organizationPhoneNumber) {
        this.organizationPhoneNumber = organizationPhoneNumber;
    }
}
