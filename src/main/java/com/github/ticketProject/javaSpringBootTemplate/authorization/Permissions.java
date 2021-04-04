package com.github.ticketProject.javaSpringBootTemplate.authorization;

import com.github.ticketProject.javaSpringBootTemplate.model.Permission;

public enum Permissions {
    //permissions will be associated with roles so that we have a more modular approach
    //to defining the authorization of each role
    //This is where we will create all the permissions that exist

    EVERYTHING("everything"),
    USER_MODIFY("user:modify"),
    USER_CREATE("user:create"),
    USER_DELETE("user:delete"),
    USER_READ("user:read");

    //this is a field of the ENUM. Which Every ENUM will have
    //ENUM is basically can be used to create objects and define them at the same time.
    private final Permission permission;

    Permissions(String permissionName) {
        this.permission = new Permission(permissionName);
    }

    public Permission getPermission() {
        return permission;
    }
}
