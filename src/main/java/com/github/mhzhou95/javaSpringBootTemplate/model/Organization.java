package com.github.mhzhou95.javaSpringBootTemplate.model;

import com.github.mhzhou95.javaSpringBootTemplate.model.Ticket;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

public class Organization implements IOrganization {
    @Id @GeneratedValue(strategy = GenerationType.AUTO) private Long id;

    private String organizationName;
    private long accountNumber;
    private List<User> contacts = new ArrayList<>();
    private List<Ticket> allUserCases = new ArrayList<>();
    private boolean isForeignAddress;
    private String state;
    private String streetAddress;
    private String zipcode;
    private String country;
    private String organizationPhoneNumber;

    public Organization() {
    }

    public Organization(Long id, String organizationName, long accountNumber, List<User> contacts, List<Ticket> allUserCases , boolean isForeignAddress, String state, String streetAddress, String zipcode, String country, String organizationPhoneNumber) {
        this.id = id;
        this.organizationName = organizationName;
        this.accountNumber = accountNumber;
        this.contacts = contacts;
        this.allUserCases = allUserCases;
        this.isForeignAddress = isForeignAddress;
        this.state = state;
        this.streetAddress = streetAddress;
        this.zipcode = zipcode;
        this.country = country;
        this.organizationPhoneNumber = organizationPhoneNumber;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getOrganizationName() {
        return organizationName;
    }

    @Override
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    @Override
    public long getAccountNumber() {
        return accountNumber;
    }

    @Override
    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public List<User> getContacts() {
        return contacts;
    }

    @Override
    public void setContacts(List<User> contacts) {
        this.contacts = contacts;
    }

    @Override
    public List<Ticket> getAllUserCases() {
        return allUserCases;
    }

////    A set contact list is probably not needed.
//    public void setAllUserCases(List<Ticket> allUserCases) {
//        this.allUserCases = allUserCases;
//    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String getStreetAddress() {
        return streetAddress;
    }

    @Override
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    @Override
    public String getZipcode() {
        return zipcode;
    }

    @Override
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String getOrganizationPhoneNumber() {
        return organizationPhoneNumber;
    }

    @Override
    public void setOrganizationPhoneNumber(String organizationPhoneNumber) {
        this.organizationPhoneNumber = organizationPhoneNumber;
    }

    @Override
    public boolean isForeignAddress() {
        return isForeignAddress;
    }

    @Override
    public void setForeignAddress(boolean foreignAddress) {
        this.isForeignAddress = foreignAddress;
    }
}
