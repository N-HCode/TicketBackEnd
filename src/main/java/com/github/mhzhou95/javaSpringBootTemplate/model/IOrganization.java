package com.github.mhzhou95.javaSpringBootTemplate.model;

import java.util.List;

public interface IOrganization {
    Long getId();

    String getOrganizationName();

    void setOrganizationName(String organizationName);

    long getAccountNumber();

    void setAccountNumber(long accountNumber);

    List<User> getContacts();

    void setContacts(List<User> contacts);

    List<Ticket> getAllUserCases();

    String getState();

    void setState(String state);

    String getStreetAddress();

    void setStreetAddress(String streetAddress);

    String getZipcode();

    void setZipcode(String zipcode);

    String getCountry();

    void setCountry(String country);

    String getOrganizationPhoneNumber();

    void setOrganizationPhoneNumber(String organizationPhoneNumber);

    boolean isForeignAddress();

    void setForeignAddress(boolean foreignAddress);
}
