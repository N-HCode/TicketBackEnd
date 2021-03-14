package com.github.mhzhou95.javaSpringBootTemplate.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.*;


@Table(name="organization",
        uniqueConstraints = {
            @UniqueConstraint(name="account_number_unique", columnNames = "account_number")
        }

)
@Entity(name = "organizations" ) //this creates a table in the database
public class Organization {

    // testing account number builder
    private static long accSeq= 1000000;

    //Id is auto generated. Do not add to constructor or create a getter/setter or it will create error.
    //Most likely the error missing default constructor for the Id
//    @Id @GeneratedValue(strategy = GenerationType.AUTO) private Long id;
    @Id
    @SequenceGenerator(name="org_id_sequence" //We create a Sequence up here
            , sequenceName = "org_id_sequence"
            , allocationSize = 1 //How much the amount get increased by
            , initialValue = 100 //this is the intial Value. By default it is one
    )
    @GeneratedValue( //BIG SERIAL DataType
            strategy = SEQUENCE,
            generator = "org_id_sequence" //We use the Sequence to generate the value
    )
    @Column(name="id", updatable = false) // Make it so noone can update it
    private Long id;

    @NotNull
    @Column(name="organization_name", nullable = false,
            columnDefinition = "TEXT")
    private String organizationName;

    @NotNull
//    @Column(unique = true) //adding a unique here will add a constraint with a random
    //name we could define the constraint using the table at the top level.
    @Column(name = "account_number")
    private long accountNumber;
    //Need this tag for collections for some reason. Don't know why it fixed the issue:
    //"Could not determine type for: java.util.Set."
    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<ClientsOrganization> clientsOrganizations = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private StatusList statusList;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private PriorityList priorityList;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Ticket> tickets= new HashSet<>();
    // @JsonManagedReference and @JsonBackReference to solve infinite recursion problem





    private boolean isForeignAddress;
    private String city;
    private String state;
    private String streetAddress;
    private String zipcode;
    private String country;
    private String organizationPhoneNumber;
    //the java.time is the newest java date API
    private final LocalDateTime dateCreated = LocalDateTime.now();
    private LocalDateTime dateModified = LocalDateTime.now();;

    //Kept getting the error "error missing default constructor"
    //For some reason adding in an empty constructor seems to solve the issue. Not sure why


    public Organization() {
    }

    public Organization(@NotNull String organizationName, boolean isForeignAddress, String city, String state, String streetAddress, String zipcode, String country, String organizationPhoneNumber, LocalDateTime dateModified) {
        this.organizationName = organizationName;
        this.isForeignAddress = isForeignAddress;
        this.city = city;
        this.state = state;
        this.streetAddress = streetAddress;
        this.zipcode = zipcode;
        this.country = country;
        this.organizationPhoneNumber = organizationPhoneNumber;
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

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public static long getAccSeq() {
        return accSeq += 100;
    }


    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
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

}
