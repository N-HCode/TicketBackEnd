package com.github.ticketProject.javaSpringBootTemplate.model;

import javax.persistence.*;

@Entity
@Table(name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private Integer permissionId;

    @Column(unique = true)
    private String permissionName;

    public Permission() {
    }

    public Permission(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionName() {
        return permissionName;
    }
}
