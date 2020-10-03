package com.github.mhzhou95.javaSpringBootTemplate.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class OrganizationContact {

    //OrganizationContact are organization/companies that our users manages.
    //We manage our users organizations, but our users have organization they manage

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) private Long id;

    //Account number and Id number are similar, however it may be good to just have
    //an account number.
    @NotNull
    @Column(unique = true)
    private long OrgContactAccountNumber;

    //organization name do not need to be unique, as we have a ID and account number
    //as identifiers. We recognize that there is a chance that two organization
    //somehow have the same name. Although, it may cause some confusion, we
    //believe the flexibility provided will be worth it.
    @NotNull
    private String organizationContactName;

    //One organization to many PersonContacts.
    @OneToMany(fetch = FetchType.LAZY)
    private Set<PersonContact> contacts = new HashSet<>();

    //One organization to many tickets
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Ticket> allContactsTickets = new HashSet<>();
    private boolean isForeignAddress;
    private String city;
    private String state;
    private String streetAddress;
    private String zipcode;
    private String country;
    private String organizationPhoneNumber;
    //the java.time is the newest java date API
    private final LocalDateTime dateCreated = LocalDateTime.now();
    private LocalDateTime dateModified = LocalDateTime.now();

    public OrganizationContact() {
    }

    public OrganizationContact(@NotNull long orgContactAccountNumber, @NotNull String organizationContactName, boolean isForeignAddress, String city, String state, String streetAddress, String zipcode, String country, String organizationPhoneNumber, LocalDateTime dateModified) {
        OrgContactAccountNumber = orgContactAccountNumber;
        this.organizationContactName = organizationContactName;
        this.isForeignAddress = isForeignAddress;
        this.city = city;
        this.state = state;
        this.streetAddress = streetAddress;
        this.zipcode = zipcode;
        this.country = country;
        this.organizationPhoneNumber = organizationPhoneNumber;
        this.dateModified = dateModified;
    }

    public String getOrganizationContactName() {
        return organizationContactName;
    }

    public void setOrganizationContactName(String organizationContactName) {
        this.organizationContactName = organizationContactName;
    }

    public Set<PersonContact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<PersonContact> contacts) {
        this.contacts = contacts;
    }

    public boolean isForeignAddress() {
        return isForeignAddress;
    }

    public void setForeignAddress(boolean foreignAddress) {
        isForeignAddress = foreignAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public LocalDateTime getDateModified() {
        return dateModified;
    }

    public void setDateModified(LocalDateTime dateModified) {
        this.dateModified = dateModified;
    }

    public Set<Ticket> getAllContactsTickets() {
        return allContactsTickets;
    }

    public void setAllContactsTickets(Set<Ticket> allContactsTickets) {
        this.allContactsTickets = allContactsTickets;
    }

    public long getOrgContactAccountNumber() {
        return OrgContactAccountNumber;
    }
}
