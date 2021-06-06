package com.github.ticketProject.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.*;
import com.github.ticketProject.javaSpringBootTemplate.authorization.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.*;

@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;
    @NotNull @Column(unique = true) String username;
    @NotNull @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;
    private String fullName;

    @NotNull
    private String email;
    private String phoneNumber;
    @NotNull
    private String userRole;
    private ZonedDateTime dateCreated;
    private ZonedDateTime lastLogin;
    private ZonedDateTime lastModified;

//    private Set<? extends GrantedAuthority> grantedAuthorities ;
    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;

    // @JsonManagedReference and @JsonBackReference to solve infinite recursion problem
//    @OneToMany(mappedBy = "user")
//    @JsonManagedReference(value = "user-tickets") //this is to mark the child elements/entities
//    @JsonIgnore
//    private Set<Ticket> tickets= new HashSet<>();

//    // @JsonManagedReference and @JsonBackReference to solve infinite recursion problem
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn( name = "organization_id" )
//    @JsonBackReference(value="organization-user") // this is to mark the parent element/entity
//    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "user_list_id" )
    @JsonBackReference(value="users-list-user") // this is to mark the parent element/entity
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userListId")
    @JsonIdentityReference(alwaysAsId = true)
    private UsersList usersList;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference(value="user-ticket_column_template_list")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private final TicketColumnTemplateList ticketColumnTemplateList = new TicketColumnTemplateList(this);


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles"
            , joinColumns = @JoinColumn (name = "user_id")
            , inverseJoinColumns = @JoinColumn (name = "role_id"))
    private final Set<Role> userRoles = new HashSet<>();


    public User() {
    }

    public User(@NotNull String username, @NotNull String password, @NotNull String firstName,@NotNull String lastName, @NotNull String email, @NotNull String userRole, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userRole = userRole;
        this.phoneNumber = phoneNumber;
        this.fullName = firstName + " " + lastName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //this is from the user details which is required
        //we are going to convert the role here to the GrantedAuthority
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        //We just use the string name of the roles to create SimpleGrantedAuthority

        //We combine all sets as sets does not have duplicate. We can do this
        //using the addAll method. We combine all permission and roles of the user
        //and then send it, so if they have multiple roles, they will have all the permissions
        //related to it.
        for (Role role : userRoles){
            authorities.addAll(role.getPermissionAndRoleAsGrantedAuthoritySet());
        }

        return authorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ZonedDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(ZonedDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public ZonedDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(ZonedDateTime lastModified) {
        this.lastModified = lastModified;
    }


    public UsersList getUsersList() {
        return usersList;
    }

    public void setUsersList(UsersList usersList) {
        this.usersList = usersList;
    }


    public TicketColumnTemplateList getTicketColumnTemplateList() {
        return ticketColumnTemplateList;
    }

    public void addRole(Role role){
        userRoles.add(role);
    }

    public Set<Role> getUserRoles() {
        return userRoles;
    }
}
