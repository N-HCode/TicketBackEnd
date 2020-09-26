package com.github.mhzhou95.javaSpringBootTemplate.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Set;

@Entity
public class OrganizationContact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) private Long id;

    @NotNull
    @Column(unique = true)
    private String organizationContactName;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<PersonContact> contacts;

    private boolean isForeignAddress;
    private String city;
    private String state;
    private String streetAddress;
    private String zipcode;
    private String country;
    private String organizationPhoneNumber;
    //the java.time is the newest java date API
    private final ZonedDateTime dateCreated = ZonedDateTime.now();
    private ZonedDateTime dateModified = ZonedDateTime.now();

    public OrganizationContact() {
    }

    public OrganizationContact(@NotNull String organizationContactName, Set<PersonContact> contacts, boolean isForeignAddress, String city, String state, String streetAddress, String zipcode, String country, String organizationPhoneNumber, ZonedDateTime dateModified) {
        this.organizationContactName = organizationContactName;
        this.contacts = contacts;
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

    public ZonedDateTime getDateModified() {
        return dateModified;
    }

    public void setDateModified(ZonedDateTime dateModified) {
        this.dateModified = dateModified;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }
}
