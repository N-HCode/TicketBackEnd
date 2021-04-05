package com.github.ticketProject.javaSpringBootTemplate.authorization;

import com.github.ticketProject.javaSpringBootTemplate.model.Role;


public enum Roles {
    //This is where we will create all the roles that exist
    //It is good practice to put ENUM labels in all CAPS
    //ENUMS are used for things that does not change. so CONSTANTS
//    ROOT(Sets.newHashSet(EVERYTHING )),
//    ADMIN(Sets.newHashSet(USER_READ,USER_MODIFY, USER_CREATE,USER_DELETE )),
//    USER(Sets.newHashSet(USER_READ));

    ROOT("ROOT"),
    ADMIN("ADMIN"),
    USER("USER");

    private final Role roleInEnum;
    private final String roleName;

    Roles(String roleName) {
        this.roleName = roleName;
        this.roleInEnum = new Role(roleName);

    }

    public Role getRoleInEnum() {
        return roleInEnum;
    }

    public String getRoleName() {
        return roleName;
    }

    //    private final Set<Permissions> permissions;
//
//    //This is the constructor that will run for every ENUM
//    Roles(Set<Permissions> permissions) {
//        this.permissions = permissions;
//    }
//
//    public Set<Permissions> getPermissions() {
//        return permissions;
//    }
    

    //This is where we can decide to use Role based authorization OR
    //permission based authorization. We will just map the whole thing to
    //GrantedAuthority because that is what UserDetail class needs
//    public Set<SimpleGrantedAuthority> grantedAuthoritySet(){
//        //This is to get all the permissions from the role so that we can use it
//        //later for when we map the ROLE and Permission to users.
//        Set<SimpleGrantedAuthority> rolePermissions = getPermissions().stream()
//                .map(permission -> new SimpleGrantedAuthority(permission.getPermissionName()))
//                .collect(Collectors.toSet());
//
//        //ROLES usually start with "ROLE_" prefix.
//        //and understands that it is a ROLE.
//        // We add this to the set of permission
//        //So that the authorities will also include the ROLE. allowing us to use
//        //role-based authorization or permission based.
//        //As this this set will have the ROLE and all the permission together
//        rolePermissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
//
//        return rolePermissions;
//
//    }
}
