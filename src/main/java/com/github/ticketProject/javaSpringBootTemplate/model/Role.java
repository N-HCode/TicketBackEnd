package com.github.ticketProject.javaSpringBootTemplate.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "roles")
public class Role {
    //Role class that will have a many to many relationship to users
    //This will be used for Role-based authorization

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private Integer roleId;

    @Column(unique = true)
    private String roleName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonManagedReference
    private final Set<Permission> permissionsInRole = new HashSet<>();

    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public Set<Permission> getPermissionsInRole() {
        return permissionsInRole;
    }

    public Set<SimpleGrantedAuthority> getPermissionAndRoleAsGrantedAuthoritySet(){
        //This is to get all the permissions from the role so that we can use it
        //later for when we map the ROLE and Permission to users.
        Set<SimpleGrantedAuthority> rolePermissions = getPermissionsInRole().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermissionName()))
                .collect(Collectors.toSet());

        //ROLES usually start with "ROLE_" prefix.
        //and understands that it is a ROLE.
        // We add this to the set of permission
        //So that the authorities will also include the ROLE. allowing us to use
        //role-based authorization or permission based.
        //As this this set will have the ROLE and all the permission together
        rolePermissions.add(new SimpleGrantedAuthority("ROLE_" + this.roleName));

        return rolePermissions;

    }

    public void addPermission(Permission permission){
        permissionsInRole.add(permission);

    }
}
