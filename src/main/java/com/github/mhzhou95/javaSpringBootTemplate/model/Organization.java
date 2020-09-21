package com.github.mhzhou95.javaSpringBootTemplate.model;

import com.github.mhzhou95.javaSpringBootTemplate.model.Ticket;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Organization {
    //Id is auto generated. Do not add to constructor or create a getter/setter or it will create error.
    //Most likely the error missing default constructor for the Id
    @Id @GeneratedValue(strategy = GenerationType.AUTO) private Long id;

    private String organizationName;
    private long accountNumber;
    //Need this tag for collections for some reason. Don't know why it fixed the issue:
    //"Could not determine type for: java.util.Set."
    @OneToMany(fetch = FetchType.LAZY)
    private Set<User> contacts;
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Ticket> allUserCases;
    private boolean isForeignAddress;
    private String state;
    private String streetAddress;
    private String zipcode;
    private String country;
    private String organizationPhoneNumber;
    private Date dateCreated;
    private Date dateModified;

    //Kept getting the error "error missing default constructor"
    //For some reason adding in an empty constructor seems to solve the issue. Not sure why
    public Organization() {
    }

    public Organization(String organizationName, long accountNumber, Set<User> contacts, Set<Ticket> allUserCases, boolean isForeignAddress, String state, String streetAddress, String zipcode, String country, String organizationPhoneNumber, Date dateCreated, Date dateModified) {
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
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }

    //Id is auto-generated so we do not have a setter.
    //However, we need this getter so that the Json response will give the Id
    //in its http response.
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


    public Set<User> getContacts() {
        return contacts;
    }


    public void setContacts(Set<User> contacts) {
        this.contacts = contacts;
    }


    public Set<Ticket> getAllUserCases() {
        return allUserCases;
    }

    public void setAllUserCases(Set<Ticket> allUserCases) {
        this.allUserCases = allUserCases;
    }


    public boolean isForeignAddress() {
        return isForeignAddress;
    }


    public void setForeignAddress(boolean foreignAddress) {
        isForeignAddress = foreignAddress;
    }


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
        return country;
    }


    public void setCountry(String country) {
        this.country = country;
    }


    public String getOrganizationPhoneNumber() {
        return organizationPhoneNumber;
    }


    public void setOrganizationPhoneNumber(String organizationPhoneNumber) {
        this.organizationPhoneNumber = organizationPhoneNumber;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }
}
