package com.github.ticketProject.javaSpringBootTemplate.model;



import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Set;

import static javax.persistence.GenerationType.*;


@Table(name="organization",
        uniqueConstraints = {
            @UniqueConstraint(name="account_number_unique", columnNames = "account_number")
        }

)
@Entity(name = "organization" ) //this creates a table in the database
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
    @Column(name="organization_id", updatable = false) // Make it so noone can update it
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
    @OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "organization-user_list")
//    @JsonBackReference can't be used for collections
//    @JsonBackReference(value="organization-user")
//    https://stackoverflow.com/questions/33475222/spring-boot-jpa-json-without-nested-object-with-onetomany-relation
    //@JsonIdentityInfo is used to indicate that object identity will be used during serialization/de-serialization.
    //https://fasterxml.github.io/jackson-annotations/javadoc/2.5/com/fasterxml/jackson/annotation/JsonIdentityInfo.html
    //https://www.javadoc.io/doc/com.fasterxml.jackson.core/jackson-annotations/2.0.4/com/fasterxml/jackson/annotation/ObjectIdGenerators.PropertyGenerator.html
    //The JsonIdentityInfo and the JsonIdentityReference will make it show that you will only get the ID
    //And not everything else in the JSON
    //The Id property in the other entity will ned a getter. otherwise, JsonIdentityInfo will not be able to get/find the property
    //You will get an error that it cannot be found.
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userListId")
    @JsonIdentityReference(alwaysAsId = true)
    private final UsersList usersList = new UsersList(this);

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "organization-client_organization_list")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "Id")
    @JsonIdentityReference(alwaysAsId = true)
    private final ClientsOrganizationList clientsOrganizationList = new ClientsOrganizationList(this);

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference(value="organization-status_list")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "statusListId")
    @JsonIdentityReference(alwaysAsId = true)
    private final StatusList statusList = new StatusList(this);

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference(value="organization-priority_list")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "priorityListId")
    @JsonIdentityReference(alwaysAsId = true)
    private final PriorityList priorityList = new PriorityList(this);

    @OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "organization-ticket_list")
    private final TicketList ticketList = new TicketList(this, clientsOrganizationList, usersList);
    // @JsonManagedReference and @JsonBackReference to solve infinite recursion problem

    private boolean isForeignAddress;
    private String city;
    private String state;
    private String streetAddress;
    private String zipcode;
    private String country;
    private String organizationPhoneNumber;
    //the java.time is the newest java date API
    private final ZonedDateTime dateCreated = ZonedDateTime.now();
    private ZonedDateTime dateModified = ZonedDateTime.now();;

    //Kept getting the error "error missing default constructor"
    //For some reason adding in an empty constructor seems to solve the issue. Not sure why


    public Organization() {

    }

    public Organization(@NotNull String organizationName, boolean isForeignAddress, String city, String state, String streetAddress, String zipcode, String country, String organizationPhoneNumber) {
//        //constructor overloading
//        this();

        statusList.setOrganization(this);
        priorityList.setOrganization(this);

        this.organizationName = organizationName;
        this.isForeignAddress = isForeignAddress;
        this.city = city;
        this.state = state;
        this.streetAddress = streetAddress;
        this.zipcode = zipcode;
        this.country = country;
        this.organizationPhoneNumber = organizationPhoneNumber;
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

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public ZonedDateTime getDateModified() {
        return dateModified;
    }

    public void setDateModified(ZonedDateTime dateModified) {
        this.dateModified = dateModified;
    }


    public StatusList getStatusList() {
        return statusList;
    }


    public PriorityList getPriorityList() {
        return priorityList;
    }


    public UsersList getUsersList() {
        return usersList;
    }
}
